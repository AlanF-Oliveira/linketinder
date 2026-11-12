package org.alan.service

import org.alan.model.Match
import org.alan.repository.MatchRepository
import org.alan.repository.RecrutadorRepository

class MatchService {

    private final MatchRepository matchRepository
    private final RecrutadorRepository recrutadorRepository

    MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository
        this.recrutadorRepository = null
    }

    MatchService(
            MatchRepository matchRepository,
            RecrutadorRepository recrutadorRepository
    ) {
        this.matchRepository = matchRepository
        this.recrutadorRepository = recrutadorRepository
    }

    List<Match> curtirVaga(int idCandidato, int idVaga) {
        return matchRepository.curtirVaga(idCandidato, idVaga)
    }

    List<Match> curtirCandidato(int idEmpresa, int idCandidato) {
        return matchRepository.curtirCandidato(idEmpresa, idCandidato)
    }

    List<Match> curtirCandidatoComoRecrutador(
            int idEmpresa, int idCandidato, int idRecrutador, String cpf, String cnpj
    ) {
        if (recrutadorRepository == null ||
                !recrutadorRepository.possuiVinculoAtivo(cpf, cnpj)) {
            throw new Exception("Recrutador sem vínculo ativo com esta empresa")
        }
        return matchRepository.curtirCandidatoComoRecrutador(
                idEmpresa, idCandidato, idRecrutador
        )
    }

    List<Match> listarPorCandidato(int idCandidato) {
        return matchRepository.listarPorCandidato(idCandidato)
    }

    List<Match> listarPorEmpresa(int idEmpresa) {
        return matchRepository.listarPorEmpresa(idEmpresa)
    }
}
