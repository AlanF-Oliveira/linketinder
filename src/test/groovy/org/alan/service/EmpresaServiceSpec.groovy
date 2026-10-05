package org.alan.service

import org.alan.dao.EmpresaDAO
import org.alan.model.Empresa
import spock.lang.Specification

class EmpresaServiceSpec extends Specification {

    EmpresaDAO empresaDAO = Stub()
    EmpresaService empresaService = new EmpresaService(empresaDAO)

    def "deve cadastrar empresa e retornar o registro com id"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")
        empresaDAO.insertEmpresa(empresa) >> 20

        when:
        Empresa resultado = empresaService.salvar(empresa)

        then:

        resultado.is(empresa)
        resultado.id == 20
    }

    def "deve informar falha quando o cadastro não gerar id"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")
        empresaDAO.insertEmpresa(empresa) >> 0

        when:
        empresaService.salvar(empresa)

        then:

        Exception erro = thrown()
        erro.message == "Falha ao cadastrar empresa"
    }


}
