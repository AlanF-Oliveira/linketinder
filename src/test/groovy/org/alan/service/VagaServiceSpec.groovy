package org.alan.service

import org.alan.model.Vaga
import org.alan.repository.VagaRepository
import spock.lang.Specification

class VagaServiceSpec extends Specification {

    VagaRepository vagaRepository = Stub()
    VagaService vagaService = new VagaService(vagaRepository)

    def "deve cadastrar vaga e retornar o registro com id"() {
        given:
        int idEmpresa = 1
        Vaga vaga = new Vaga(titulo: "Desenvolvedor Java")
        vagaRepository.inserir(vaga, idEmpresa) >> 10

        when:
        Vaga resultado = vagaService.criarVaga(vaga, idEmpresa)

        then:
        resultado.is(vaga)
        resultado.id == 10
    }

    def "deve informar falha quando o cadastro nao gerar id"() {
        given:
        int idEmpresa = 1
        Vaga vaga = new Vaga(titulo: "Desenvolvedor Java")
        vagaRepository.inserir(vaga, idEmpresa) >> 0

        when:
        vagaService.criarVaga(vaga, idEmpresa)

        then:
        Exception erro = thrown()
        erro.message == "Falha ao cadastrar vaga"
    }

    def "deve listar vagas"() {
        given:
        List<Vaga> vagas = [
                new Vaga(id: 1, titulo: "Desenvolvedor Java"),
                new Vaga(id: 2, titulo: "Desenvolvedor Frontend")
        ]
        vagaRepository.listarVagas() >> vagas

        when:
        List<Vaga> resultado = vagaService.listarVagas()

        then:
        resultado == vagas
        resultado.size() == 2
    }

    def "deve buscar vaga por id"() {
        given:
        int idVaga = 1
        Vaga vaga = new Vaga(id: idVaga, titulo: "Desenvolvedor Java")
        vagaRepository.buscarVagaPorId(idVaga) >> vaga

        when:
        Vaga resultado = vagaService.buscarVagaPorId(idVaga)

        then:
        resultado.is(vaga)
    }

    def "deve informar quando vaga nao for encontrada"() {
        given:
        int idVaga = 99
        vagaRepository.buscarVagaPorId(idVaga) >> null

        when:
        vagaService.buscarVagaPorId(idVaga)

        then:
        Exception erro = thrown()
        erro.message == "Vaga não encontrada"
    }

    def "deve atualizar vaga"() {
        given:
        Vaga vaga = new Vaga(id: 1, titulo: "Desenvolvedor Java Sênior")
        vagaRepository.atualizarVaga(vaga) >> true

        when:
        Vaga resultado = vagaService.atualizarVaga(vaga)

        then:
        resultado.is(vaga)
    }

    def "deve informar falha ao atualizar vaga inexistente"() {
        given:
        Vaga vaga = new Vaga(id: 99, titulo: "Vaga inexistente")
        vagaRepository.atualizarVaga(vaga) >> false

        when:
        vagaService.atualizarVaga(vaga)

        then:
        Exception erro = thrown()
        erro.message == "Vaga não encontrada"
    }

    def "deve deletar vaga"() {
        given:
        int idVaga = 1
        vagaRepository.deletarVaga(idVaga) >> true

        when:
        vagaService.deletarVaga(idVaga)

        then:
        noExceptionThrown()
    }

    def "deve informar falha ao deletar vaga inexistente"() {
        given:
        int idVaga = 99
        vagaRepository.deletarVaga(idVaga) >> false

        when:
        vagaService.deletarVaga(idVaga)

        then:
        Exception erro = thrown()
        erro.message == "Vaga não encontrada"
    }
}