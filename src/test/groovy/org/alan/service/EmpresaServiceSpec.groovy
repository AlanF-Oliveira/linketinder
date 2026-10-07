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
        empresaDAO.inserir(empresa) >> 20

        when:
        Empresa resultado = empresaService.salvar(empresa)

        then:

        resultado.is(empresa)
        resultado.id == 20
    }

    def "deve informar falha quando o cadastro não gerar id"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")
        empresaDAO.inserir(empresa) >> 0

        when:
        empresaService.salvar(empresa)

        then:

        Exception erro = thrown()
        erro.message == "Falha ao cadastrar empresa"
    }

    def "deve buscar empresa por cnpj"() {
        given:
        String cnpj = "12345678000199"
        Empresa empresa = new Empresa(nome: "Empresa Teste", cnpj: cnpj)
        empresaDAO.buscarEmpresaPorCnpj(cnpj) >> empresa

        when:
        Empresa resultado = empresaService.buscarPorCnpj(cnpj)

        then:
        resultado.is(empresa)
    }

    def "deve informar quando empresa nao for encontrada"() {
        given:
        String cnpj = "00000000000000"
        empresaDAO.buscarEmpresaPorCnpj(cnpj) >> null

        when:
        empresaService.buscarPorCnpj(cnpj)

        then:
        Exception erro = thrown()
        erro.message == "Empresa não encontrada"
    }

    def "deve listar empresas"() {
        given:
        List<Empresa> empresas = [new Empresa(nome: "Empresa A", cnpj: "111"), new Empresa(nome: "Empresa B", cnpj: "222")]
        empresaDAO.listarEmpresas() >> empresas

        when:
        List<Empresa> resultado = empresaService.listarEmpresas()

        then:
        resultado == empresas
        resultado.size() == 2
    }

    def "deve atualizar empresa"() {
        given:
        Empresa empresa = new Empresa(nome: "Empresa Atualizada", cnpj: "123")
        empresaDAO.atualizarEmpresa(empresa) >> true

        when:
        Empresa resultado = empresaService.atualizarEmpresa(empresa)

        then:
        resultado.is(empresa)
    }

    def "deve informar falha ao atualizar empresa inexistente"() {
        given:
        Empresa empresa = new Empresa(nome: "Empresa Inexistente", cnpj: "000")
        empresaDAO.atualizarEmpresa(empresa) >> false

        when:
        empresaService.atualizarEmpresa(empresa)

        then:
        Exception erro = thrown()
        erro.message == "Empresa não encontrada"
    }

    def "deve deletar empresa"() {
        given:
        String cnpj = "123"
        empresaDAO.deletarEmpresa(cnpj) >> true

        when:
        empresaService.deletarEmpresa(cnpj)

        then:
        noExceptionThrown()
    }

    def "deve informar falha ao deletar empresa inexistente"() {
        given:
        String cnpj = "000"
        empresaDAO.deletarEmpresa(cnpj) >> false

        when:
        empresaService.deletarEmpresa(cnpj)

        then:
        Exception erro = thrown()
        erro.message == "Empresa não encontrada"
    }

}
