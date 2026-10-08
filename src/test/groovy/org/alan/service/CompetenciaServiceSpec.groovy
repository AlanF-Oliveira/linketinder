package org.alan.service

import org.alan.dao.CompetenciaDAO
import org.alan.repository.CompetenciaRepository
import spock.lang.Specification

class CompetenciaServiceSpec extends Specification {

    CompetenciaRepository competenciaRepository = Stub()
    CompetenciaService competenciaService =
            new CompetenciaService(competenciaRepository)

    def "deve listar competencias"() {
        given:
        List<String> competencias = ["1 - JAVA", "2 - SQL"]
        competenciaRepository.listarCompetencias() >> competencias

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
        competenciaRepository.atualizarCompetencia(novoNome, idCompetencia) >> false

        when:
        competenciaService.atualizarCompetencia(novoNome, idCompetencia)

        then:
        Exception erro = thrown()
        erro.message == "Competência não encontrada"
    }

    def "deve deletar competencia"() {
        given:
        int idCompetencia = 1
        competenciaRepository.deletarCompetencia(idCompetencia) >> true

        when:
        competenciaService.deletarCompetencia(idCompetencia)

        then:
        noExceptionThrown()
    }

    def "deve informar falha ao deletar competencia inexistente"() {
        given:
        int idCompetencia = 99
        competenciaRepository.deletarCompetencia(idCompetencia) >> false

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
        competenciaRepository.atualizarCompetencia(
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