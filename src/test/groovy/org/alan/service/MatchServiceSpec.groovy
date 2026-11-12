package org.alan.service

import org.alan.model.Match
import org.alan.repository.MatchRepository
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
}
