package org.alan.model

import spock.lang.Specification

class EmpresaSpec extends Specification {

    def "deve atualizar os dados da empresa"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")

        when:
        empresa.nome = "Oh My Dog!"
        empresa.email = "ohdog@dog.com"
        empresa.cnpj = "1234"
        empresa.pais = "Portugal"
        empresa.estado = "Lisboa"
        empresa.cep = "456"
        empresa.descricao = "Hot dog em Portugal"

        then:
        empresa.nome == "Oh My Dog!"
        empresa.email == "ohdog@dog.com"
        empresa.cnpj == "1234"
        empresa.pais == "Portugal"
        empresa.estado == "Lisboa"
        empresa.cep == "456"
        empresa.descricao == "Hot dog em Portugal"
    }
}
