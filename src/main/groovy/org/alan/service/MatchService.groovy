package org.alan.service

import org.alan.model.Match
import org.alan.repository.MatchRepository

class MatchService {

    private final MatchRepository matchRepository

    MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository
    }

    List<Match> curtirVaga(int idCandidato, int idVaga) {
        return matchRepository.curtirVaga(idCandidato, idVaga)
    }

    List<Match> curtirCandidato(int idEmpresa, int idCandidato) {
        return matchRepository.curtirCandidato(idEmpresa, idCandidato)
    }

    List<Match> listarPorCandidato(int idCandidato) {
        return matchRepository.listarPorCandidato(idCandidato)
    }

    List<Match> listarPorEmpresa(int idEmpresa) {
        return matchRepository.listarPorEmpresa(idEmpresa)
    }
}
