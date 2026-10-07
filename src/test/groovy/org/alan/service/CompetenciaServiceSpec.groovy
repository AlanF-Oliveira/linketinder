package org.alan.service

import org.alan.dao.CompetenciaDAO
import spock.lang.Specification

class CompetenciaServiceSpec extends Specification {

    CompetenciaDAO competenciaDAO = Stub()
    CompetenciaService competenciaService =
            new CompetenciaService(competenciaDAO)

    def "deve listar competencias"() {
        given:
        List<String> competencias = ["1 - JAVA", "2 - SQL"]
        competenciaDAO.listarCompetencias() >> competencias

        when:
        List<String> resultado =
                competenciaService.listarCompetencias()

        then:
        resultado == competencias
        resultado.size() == 2
    }

    def "deve informar falha ao atualizar competencia inexistente"() {
        given:
        int idCompetencia = 99
        String novoNome = "GROOVY"
        competenciaDAO.atualizarCompetencia(novoNome, idCompetencia) >> false

        when:
        competenciaService.atualizarCompetencia(novoNome, idCompetencia)

        then:
        Exception erro = thrown()
        erro.message == "Competência não encontrada"
    }

    def "deve deletar competencia"() {
        given:
        int idCompetencia = 1
        competenciaDAO.deletarCompetencia(idCompetencia) >> true

        when:
        competenciaService.deletarCompetencia(idCompetencia)

        then:
        noExceptionThrown()
    }

    def "deve informar falha ao deletar competencia inexistente"() {
        given:
        int idCompetencia = 99
        competenciaDAO.deletarCompetencia(idCompetencia) >> false

        when:
        competenciaService.deletarCompetencia(idCompetencia)

        then:
        Exception erro = thrown()
        erro.message == "Competência não encontrada"
    }

    def "deve atualizar competencia"() {
        given:
        int idCompetencia = 1
        String novoNome = "GROOVY"
        competenciaDAO.atualizarCompetencia(
                novoNome,
                idCompetencia
        ) >> true

        when:
        boolean resultado = competenciaService.atualizarCompetencia(
                novoNome,
                idCompetencia
        )

        then:
        resultado
    }
}