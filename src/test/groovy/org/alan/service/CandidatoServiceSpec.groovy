package org.alan.service

import org.alan.model.Candidato
import org.alan.repository.CandidatoRepository
import spock.lang.Specification

class CandidatoServiceSpec extends Specification {

    CandidatoRepository candidatoRepository
    CandidatoService candidatoService
    Candidato candidato

    def setup() {
        candidatoRepository = Stub()
        candidatoService = new CandidatoService(candidatoRepository)
        candidato = new Candidato(nome: "Alan", cpf: "123")
    }

    def "deve cadastrar candidato com sucesso"() {
        given:
        candidatoRepository.inserir(candidato) >> 10

        when:
        Candidato resultado = candidatoService.salvar(candidato)

        then:
        resultado.is(candidato)
        resultado.id == 10
    }

    def "deve informar falha quando o cadastro não gerar id"() {
        given:
        candidatoRepository.inserir(candidato) >> 0

        when:
        candidatoService.salvar(candidato)

        then:
        Exception erro = thrown()
        erro.message == "Falha ao cadastrar candidato"
    }

    def "deve buscar candidato por CPF"() {
        given:
        candidatoRepository.buscarCandidatoPorCpf("123") >> candidato

        when:
        Candidato resultado = candidatoService.buscarPorCpf("123")

        then:
        resultado.is(candidato)
    }

    def "deve informar erro quando candidato não for encontrado"() {
        given:
        candidatoRepository.buscarCandidatoPorCpf("123") >> null

        when:
        candidatoService.buscarPorCpf("123")

        then:
        Exception erro = thrown()
        erro.message == "Candidato não encontrado"
    }

    def "deve listar candidatos"() {
        given:
        Candidato outroCandidato = new Candidato(nome: "Maria", cpf: "456")
        List<Candidato> candidatosEsperados = [candidato, outroCandidato]
        candidatoRepository.listarCandidatos() >> candidatosEsperados

        when:
        List<Candidato> resultado = candidatoService.listarCandidatos()

        then:
        resultado == candidatosEsperados
    }

    def "deve atualizar candidato com sucesso"() {
        given:
        candidatoRepository.atualizarCandidato(candidato) >> true

        when:
        Candidato resultado = candidatoService.atualizarCandidato(candidato)

        then:
        resultado.is(candidato)
    }

    def "deve informar erro ao atualizar candidato inexistente"() {
        given:
        candidatoRepository.atualizarCandidato(candidato) >> false

        when:
        candidatoService.atualizarCandidato(candidato)

        then:
        Exception erro = thrown()
        erro.message == "Candidato não encontrado"
    }

    def "deve deletar candidato com sucesso"() {
        given:
        candidatoRepository.deletarCandidato("123") >> true

        when:
        candidatoService.deletarCandidato("123")

        then:
        noExceptionThrown()
    }

    def "deve informar erro ao deletar candidato inexistente"() {
        given:
        candidatoRepository.deletarCandidato("123") >> false

        when:
        candidatoService.deletarCandidato("123")

        then:
        Exception erro = thrown()
        erro.message == "Candidato não encontrado"
    }
}