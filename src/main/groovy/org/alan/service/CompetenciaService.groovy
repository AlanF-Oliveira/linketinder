package org.alan.service

import org.alan.database.BancoDeDados

class CompetenciaService {
    BancoDeDados bd;

    CompetenciaService(BancoDeDados bd) {
        this.bd = bd
    }

    List<String> listarCompetencias() {
        return bd.listarCompetencias()
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        boolean atualizou = bd.atualizarCompetencia(competencia, idCompetencia)
        if (!atualizou) {
            throw new Exception("Competência não encontrada")
        }
        return atualizou
    }

    void deletarCompetencia(int idCompetencia) {
        boolean deletou = bd.deletarCompetencia(idCompetencia)
        if (!deletou) {
            throw new Exception("Competência não encontrada")
        }
    }
}
