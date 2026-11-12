package org.alan.service

import org.alan.model.Match
import org.alan.repository.MatchRepository
import org.alan.repository.RecrutadorRepository
import spock.lang.Specification

class MatchServiceSpec extends Specification {

    MatchRepository matchRepository = Stub()
    MatchService matchService = new MatchService(matchRepository)

    def "deve registrar curtida do candidato e retornar matches criados"() {
        given:
        List<Match> matches = [new Match(id: 1, idVaga: 2, idCandidato: 3)]
        matchRepository.curtirVaga(3, 2) >> matches

        when:
        List<Match> resultado = matchService.curtirVaga(3, 2)

        then:
        resultado == matches
    }

    def "deve listar matches da empresa"() {
        given:
        List<Match> matches = [new Match(idEmpresa: 4)]
        matchRepository.listarPorEmpresa(4) >> matches

        when:
        List<Match> resultado = matchService.listarPorEmpresa(4)

        then:
        resultado == matches
    }

    def "deve registrar curtida de recrutador com vinculo ativo"() {
        given:
        RecrutadorRepository recrutadores = Stub()
        recrutadores.possuiVinculoAtivo("123", "456") >> true
        MatchRepository matches = Stub()
        List<Match> esperados = [new Match(idEmpresa: 4, idCandidato: 3)]
        matches.curtirCandidatoComoRecrutador(4, 3, 8) >> esperados
        MatchService service = new MatchService(matches, recrutadores)

        when:
        List<Match> resultado = service.curtirCandidatoComoRecrutador(
                4, 3, 8, "123", "456"
        )

        then:
        resultado == esperados
    }

    def "deve bloquear curtida de recrutador sem vinculo ativo"() {
        given:
        RecrutadorRepository recrutadores = Stub()
        recrutadores.possuiVinculoAtivo("123", "456") >> false
        MatchService service = new MatchService(
                Stub(MatchRepository), recrutadores
        )

        when:
        service.curtirCandidatoComoRecrutador(4, 3, 8, "123", "456")

        then:
        def erro = thrown(Exception)
        erro.message == "Recrutador sem vínculo ativo com esta empresa"
    }

    def "deve encaminhar a curtida do recrutador com todos os identificadores"() {
        given:
        RecrutadorRepository recrutadores = Mock()
        MatchRepository matches = Mock()
        recrutadores.possuiVinculoAtivo("555", "123") >> true
        MatchService service = new MatchService(matches, recrutadores)

        when:
        service.curtirCandidatoComoRecrutador(7, 9, 12, "555", "123")

        then:
        1 * matches.curtirCandidatoComoRecrutador(7, 9, 12) >> []
    }

    def "deve validar o vínculo antes de acessar o repositório de matches"() {
        given:
        RecrutadorRepository recrutadores = Mock()
        MatchRepository matches = Mock()
        MatchService service = new MatchService(matches, recrutadores)

        when:
        service.curtirCandidatoComoRecrutador(7, 9, 12, "555", "123")

        then:
        1 * recrutadores.possuiVinculoAtivo("555", "123") >> false
        0 * matches._
        Exception erro = thrown()
        erro.message == "Recrutador sem vínculo ativo com esta empresa"
    }
}
