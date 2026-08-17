package org.alan.model


import spock.lang.Specification;


class CandidatoSpec extends Specification {
    Candidato candidato;

    def "deve atualizar todos os campos do Candidato através dos setters"() {

        given:
        candidato = new Candidato(
                nome: "Alan",
                idade: 32,
                email: "alan@gmail.com",
                cpf: "123",
                descricao: "dev",
                estado: "Ceará",
                cep: "600",
                competencias: ["Spring Boot, Java, PostgreSQL"]);

        when:
        candidato.setNome("Joao")
        candidato.setIdade(33)
        candidato.setEmail("joao@gmail.com")
        candidato.setCpf("2321")
        candidato.setDescricao("Desenvolvedor Pleno")
        candidato.setEstado("Rio de Janeiro")
        candidato.setCep("6002")
        candidato.setCompetencias(["Spring Boot, Java, MongoDB"])

        then:
        candidato.nome == "Joao";
        candidato.idade == 33
        candidato.email == "joao@gmail.com"
        candidato.cpf == "2321"
        candidato.descricao == "Desenvolvedor Pleno"
        candidato.estado == "Rio de Janeiro"
        candidato.cep == "6002"
        candidato.competencias == ["Spring Boot, Java, MongoDB"]

    }

}