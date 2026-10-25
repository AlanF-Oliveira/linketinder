package org.alan.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.alan.controller.CandidatoController
import org.alan.model.Candidato

import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeParseException

class CandidatoHttpHandler implements HttpHandler {

    private final CandidatoController candidatoController

    CandidatoHttpHandler(CandidatoController candidatoController) {
        this.candidatoController = candidatoController
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
                    "nome", "sobrenome", "nascimento", "email", "cpf",
                    "descricao", "pais", "estado", "cidade", "cep", "senha"
            ]

            if (campos.any { !dados[it] } ||
                    (dados.senha as String).length() < 6 ||
                    (dados.competencias != null &&
                            !(dados.competencias instanceof List))) {
                throw new IllegalArgumentException()
            }

            Candidato candidato = new Candidato(
                    nome: dados.nome,
                    sobrenome: dados.sobrenome,
                    nascimento: LocalDate.parse(dados.nascimento as String),
                    email: dados.email,
                    cpf: dados.cpf,
                    descricao: dados.descricao,
                    pais: dados.pais,
                    estado: dados.estado,
                    cidade: dados.cidade,
                    cep: dados.cep,
                    senha: dados.senha,
                    competencias: dados.competencias ?: []
            )

            Candidato salvo = candidatoController.salvar(candidato)
            responder(exchange, 201, [id: salvo.id])

        } catch (Exception erro) {
            if (erro instanceof SQLException && erro.SQLState == "23505") {
                responder(exchange, 409, [erro: "CPF já cadastrado"])
            } else if (erro instanceof JsonException ||
                    erro instanceof IllegalArgumentException ||
                    erro instanceof DateTimeParseException) {
                responder(exchange, 400, [erro: "Dados inválidos"])
            } else {
                responder(exchange, 500, [erro: "Erro ao cadastrar candidato"])
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