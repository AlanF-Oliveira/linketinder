package org.alan.repository

interface CompetenciaRepository {

    int buscarOuCriarCompetencia(String competencia)

    List<String> listarCompetencias()

    boolean atualizarCompetencia(String competencia, int idCompetencia)

    boolean deletarCompetencia(int idCompetencia)
}