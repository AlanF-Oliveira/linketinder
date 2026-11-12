package org.alan.service

import org.alan.model.Empresa
import org.alan.model.Recrutador
import org.alan.repository.EmpresaRepository
import org.alan.repository.RecrutadorRepository
import spock.lang.Specification

import java.time.LocalDate

class RecrutadorServiceSpec extends Specification {

    RecrutadorRepository recrutadorRepository = Stub()
    EmpresaRepository empresaRepository = Stub()
    RecrutadorService recrutadorService = new RecrutadorService(
            recrutadorRepository,
            empresaRepository
    )

    def "deve cadastrar recrutador vinculado à empresa informada"() {
        given:
        Empresa empresa = new Empresa(id: 7, cnpj: "123")
        Recrutador recrutador = novoRecrutador()
        empresaRepository.buscarEmpresaPorCnpj("123") >> empresa
        recrutadorRepository.buscarPorCpf(recrutador.cpf) >> null
        recrutadorRepository.inserir(recrutador, 7) >> 12

        when:
        Recrutador resultado = recrutadorService.salvar(recrutador, "123")

        then:
        resultado.is(recrutador)
        resultado.id == 12
        resultado.empresa.is(empresa)
        resultado.status == "ATIVO"
    }

    def "não deve cadastrar recrutador em empresa inexistente"() {
        given:
        empresaRepository.buscarEmpresaPorCnpj("999") >> null

        when:
        recrutadorService.salvar(novoRecrutador(), "999")

        then:
        Exception erro = thrown()
        erro.message == "Empresa não encontrada"
    }

    def "não deve cadastrar CPF já existente"() {
        given:
        Empresa empresa = new Empresa(id: 7, cnpj: "123")
        Recrutador existente = novoRecrutador()
        empresaRepository.buscarEmpresaPorCnpj("123") >> empresa
        recrutadorRepository.buscarPorCpf(existente.cpf) >> existente

        when:
        recrutadorService.salvar(novoRecrutador(), "123")

        then:
        Exception erro = thrown()
        erro.message == "Recrutador com CPF já cadastrado"
    }

    def "deve listar apenas recrutadores da empresa existente"() {
        given:
        Empresa empresa = new Empresa(id: 7, cnpj: "123")
        List<Recrutador> recrutadores = [novoRecrutador()]
        empresaRepository.buscarEmpresaPorCnpj("123") >> empresa
        recrutadorRepository.listarPorEmpresa("123") >> recrutadores

        expect:
        recrutadorService.listarPorEmpresa("123") == recrutadores
    }

    def "não deve atualizar recrutador de outra empresa"() {
        given:
        Recrutador recrutador = novoRecrutador()
        recrutadorRepository.buscarPorCpfNaEmpresa("555", "123") >> null

        when:
        recrutadorService.atualizar(recrutador, "123")

        then:
        Exception erro = thrown()
        erro.message == "Recrutador não encontrado para esta empresa"
    }

    def "não deve remover recrutador de outra empresa"() {
        given:
        recrutadorRepository.buscarPorCpfNaEmpresa("555", "123") >> null

        when:
        recrutadorService.deletar("555", "123")

        then:
        Exception erro = thrown()
        erro.message == "Recrutador não encontrado para esta empresa"
    }

    def "deve rejeitar dados fora da política estrutural"() {
        given:
        Recrutador recrutador = novoRecrutador()
        recrutador.senha = "123"

        when:
        recrutadorService.salvar(recrutador, "123")

        then:
        Exception erro = thrown()
        erro.message == "Dados do recrutador inválidos"
    }

    def "deve consultar vínculo ativo"() {
        given:
        recrutadorRepository.possuiVinculoAtivo("555", "123") >> true

        expect:
        recrutadorService.possuiVinculoAtivo("555", "123")
    }

    private static Recrutador novoRecrutador() {
        new Recrutador(
                nome: "Marina",
                areaAtuacao: "TI",
                descricao: "Seleção técnica",
                email: "marina@example.com",
                cpf: "555",
                nascimento: LocalDate.of(1990, 1, 1),
                senha: "senha123"
        )
    }
}
