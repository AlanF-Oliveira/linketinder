package org.alan.model

import spock.lang.Specification

import java.time.LocalDate

class CandidatoSpec extends Specification {

    def "deve atualizar os dados do candidato"() {
        given:
        Candidato candidato = new Candidato(nome: "Alan", cpf: "123")
        LocalDate nascimento = LocalDate.of(1993, 5, 12)

        when:
        candidato.nome = "João"
        candidato.sobrenome = "Silva"
        candidato.nascimento = nascimento
        candidato.email = "joao@gmail.com"
        candidato.cpf = "456"
        candidato.descricao = "Desenvolvedor"
        candidato.estado = "Rio de Janeiro"
        candidato.cep = "6002"
        candidato.competencias = ["Java", "PostgreSQL"]

        then:
        candidato.nome == "João"
        candidato.sobrenome == "Silva"
        candidato.nascimento == nascimento
        candidato.email == "joao@gmail.com"
        candidato.cpf == "456"
        candidato.descricao == "Desenvolvedor"
        candidato.estado == "Rio de Janeiro"
        candidato.cep == "6002"
        candidato.competencias == ["Java", "PostgreSQL"]
    }
}
