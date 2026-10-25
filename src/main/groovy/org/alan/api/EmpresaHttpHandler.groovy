package org.alan.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.alan.controller.EmpresaController
import org.alan.model.Empresa

import java.sql.SQLException

class EmpresaHttpHandler implements HttpHandler {

    private final EmpresaController empresaController

    EmpresaHttpHandler(EmpresaController empresaController) {
        this.empresaController = empresaController
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
                    "nome", "email", "cnpj", "descricao",
                    "pais", "estado", "cidade", "cep", "senha"
            ]

            if (campos.any { !dados[it] } ||
                    (dados.senha as String).length() < 6) {
                throw new IllegalArgumentException()
            }

            Empresa empresa = new Empresa(
                    nome: dados.nome,
                    email: dados.email,
                    cnpj: dados.cnpj,
                    descricao: dados.descricao,
                    pais: dados.pais,
                    estado: dados.estado,
                    cidade: dados.cidade,
                    cep: dados.cep,
                    senha: dados.senha
            )

            Empresa salva = empresaController.salvar(empresa)
            responder(exchange, 201, [id: salva.id])

        } catch (Exception erro) {
            if (erro instanceof SQLException && erro.SQLState == "23505") {
                responder(exchange, 409, [erro: "CNPJ já cadastrado"])
            } else if (erro instanceof JsonException ||
                    erro instanceof IllegalArgumentException) {
                responder(exchange, 400, [erro: "Dados inválidos"])
            } else {
                responder(exchange, 500, [erro: "Erro ao cadastrar empresa"])
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