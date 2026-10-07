package org.alan.service

import org.alan.dao.CompetenciaDAO
import org.alan.database.BancoDeDados

class CompetenciaService {
    private final CompetenciaDAO competenciaDAO

    CompetenciaService(CompetenciaDAO competenciaDAO) {
        this.competenciaDAO = competenciaDAO
    }

    List<String> listarCompetencias() {
        return competenciaDAO.listarCompetencias()
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        boolean atualizou = competenciaDAO.atualizarCompetencia(competencia, idCompetencia)
        if (!atualizou) {
            throw new Exception("Competência não encontrada")
        }
        return atualizou
    }

    void deletarCompetencia(int idCompetencia) {
        boolean deletou = competenciaDAO.deletarCompetencia(idCompetencia)
        if (!deletou) {
            throw new Exception("Competência não encontrada")
        }
    }
}
