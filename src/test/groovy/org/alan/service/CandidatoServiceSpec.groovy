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
        candidato = new Candidato(
                nome: "Alan",
                email: "alan@gmail.com",
                cpf: "123",
                descricao: "dev",
                estado: "Ceará",
                cep: "600",
                competencias: ["Spring Boot, Java, PostgreSQL"]);
        when:
        candidatoService.salvar(candidato)

        then:
        listaCandidatos.nome == "Alan"
        listaCandidatos.email == "alan@gmail.com"
        listaCandidatos.cpf == "123"
        listaCandidatos.descricao == "dev"
        listaCandidatos.estado == "Ceará"
        listaCandidatos.cep == "600"
        listaCandidatos.competencias == ["Spring Boot, Java, PostgreSQL"]


    }
}

