package org.alan.service


import org.alan.repository.CompetenciaRepository

class CompetenciaService {
    private final CompetenciaRepository competenciaRepository

    CompetenciaService(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository
    }

    List<String> listarCompetencias() {
        return competenciaRepository.listarCompetencias()
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        boolean atualizou = competenciaRepository.atualizarCompetencia(competencia, idCompetencia)
        if (!atualizou) {
            throw new Exception("Competência não encontrada")
        }
        return atualizou
    }

    void deletarCompetencia(int idCompetencia) {
        boolean deletou = competenciaRepository.deletarCompetencia(idCompetencia)
        if (!deletou) {
            throw new Exception("Competência não encontrada")
        }
    }
}
