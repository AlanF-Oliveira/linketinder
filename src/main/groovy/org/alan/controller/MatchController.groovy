package org.alan.controller

import org.alan.model.Match
import org.alan.service.MatchService

class MatchController {

    private final MatchService matchService

    MatchController(MatchService matchService) {
        this.matchService = matchService
    }

    List<Match> curtirVaga(int idCandidato, int idVaga) {
        return matchService.curtirVaga(idCandidato, idVaga)
    }

    List<Match> curtirCandidato(int idEmpresa, int idCandidato) {
        return matchService.curtirCandidato(idEmpresa, idCandidato)
    }

    List<Match> listarPorCandidato(int idCandidato) {
        return matchService.listarPorCandidato(idCandidato)
    }

    List<Match> listarPorEmpresa(int idEmpresa) {
        return matchService.listarPorEmpresa(idEmpresa)
    }
}
