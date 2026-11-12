package org.alan.repository

import org.alan.model.Match

interface MatchRepository {

    List<Match> curtirVaga(int idCandidato, int idVaga)

    List<Match> curtirCandidato(int idEmpresa, int idCandidato)

    List<Match> curtirCandidatoComoRecrutador(
            int idEmpresa, int idCandidato, int idRecrutador
    )

    List<Match> listarPorCandidato(int idCandidato)

    List<Match> listarPorEmpresa(int idEmpresa)
}
