package org.alan.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.alan.controller.VagaController
import org.alan.model.Vaga

class VagaHttpHandler implements HttpHandler {

    private final VagaController vagaController

    VagaHttpHandler(VagaController vagaController) {
        this.vagaController = vagaController
    }

    @Override
    void handle(HttpExchange exchange) {
        if (exchange.requestMethod != "POST") {
            exchange.responseHeaders.set("Allow", "POST")
            exchange.sendResponseHeaders(405, -1)
            exchange.close()
            return
        }

        try {
            def dados = new JsonSlurper().parseText(
                    exchange.requestBody.getText("UTF-8")
            )

            if (!(dados instanceof Map)) {
                throw new IllegalArgumentException()
            }

            List<String> campos = [
                    "titulo", "descricao", "estado", "cidade", "cnpj"
            ]

            if (campos.any { !dados[it] } ||
                    (dados.competenciasExigidas != null &&
                            !(dados.competenciasExigidas instanceof List))) {
                throw new IllegalArgumentException()
            }

            Vaga vaga = new Vaga(
                    titulo: dados.titulo,
                    descricao: dados.descricao,
                    estado: dados.estado,
                    cidade: dados.cidade,
                    competenciasExigidas: dados.competenciasExigidas ?: []
            )

            Vaga salva = vagaController.criarVaga(vaga, dados.cnpj as String)
            responder(exchange, 201, [id: salva.id])

        } catch (Exception erro) {
            if (erro instanceof JsonException ||
                    erro instanceof IllegalArgumentException) {
                responder(exchange, 400, [erro: "Dados inválidos"])
            } else {
                responder(exchange, 500, [erro: "Erro ao cadastrar vaga"])
            }
        } finally {
            exchange.close()
        }
    }

    private static void responder(HttpExchange exchange, int status, Map corpo) {
        byte[] resposta = JsonOutput.toJson(corpo).getBytes("UTF-8")
        exchange.responseHeaders.set(
                "Content-Type", "application/json; charset=UTF-8"
        )
        exchange.sendResponseHeaders(status, resposta.length)
        exchange.responseBody.write(resposta)
    }
}