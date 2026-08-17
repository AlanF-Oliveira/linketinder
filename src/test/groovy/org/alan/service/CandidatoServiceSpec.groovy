package org.alan.service

import org.alan.Model.Candidato
import spock.lang.Specification

class CandidatoServiceSpec extends Specification {

    List<Candidato> listaCandidatos
    CandidatoService candidatoService

    def setup() {
        listaCandidatos = []
        candidatoService = new CandidatoService(listaCandidatos)
    }

    def "deve cadastrar candidato com sucesso"() {

        given:
        Candidato candidato = new Candidato(
                nome: "Alan",
                email: "alan@gmail.com",
                cpf: "123",
                descricao: "dev",
                estado: "Ceará",
                cep: "600",
                idade: 32,
                competencias: ["Spring Boot, Java, PostgreSQL"]);
        when:
        candidatoService.salvar(candidato)

        then:
        listaCandidatos.size() == 1
        listaCandidatos[0].nome == "Alan"
        listaCandidatos[0].cpf == "123"


    }
}

