package org.alan.controller

import org.alan.service.CompetenciaService

class CompetenciaController {

    private final CompetenciaService competenciaService

    CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService
    }

    List<String> listarCompetencias() {
        return competenciaService.listarCompetencias()
    }

    boolean atualizarCompetencia(String nome, int id) {
        return competenciaService.atualizarCompetencia(nome, id)
    }

    void deletarCompetencia(int id) {
        competenciaService.deletarCompetencia(id)
    }
}