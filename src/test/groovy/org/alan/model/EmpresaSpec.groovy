package org.alan.model


import spock.lang.Specification

class EmpresaSpec extends Specification {

    Empresa empresa;

    def "deve atualizar todos os campos da Empresa através dos setters"() {

        given:
        empresa = new Empresa(
                nome: "Dogão do ratão",
                email: "dogratao@ratoes.com",
                cnpj: "5678",
                pais: "Brasil",
                estado: "São Paulo",
                cep: "123",
                descricao: "O melhor podrão de São Paulo",
                competenciasDesejadas: ["Cozinhar", "Atender", "Lavar pratos"])

        when:
        empresa.setNome("Oh My Dog!")
        empresa.setEmail("ohdog@dog.com")
        empresa.setCnpj("1234")
        empresa.setPais("Portugal")
        empresa.setEstado("Lisboa")
        empresa.setCep("456")
        empresa.setDescricao("O melhor hot dog de Portugal")
        empresa.setCompetenciasDesejadas(["Atender"])

        then:
        empresa.nome == "Oh My Dog!";
        empresa.email == "ohdog@dog.com"
        empresa.cnpj == "1234"
        empresa.pais == "Portugal"
        empresa.estado == "Lisboa"
        empresa.cep == "456"
        empresa.descricao == "O melhor hot dog de Portugal"
        empresa.competenciasDesejadas == ["Atender"]

    }
}

