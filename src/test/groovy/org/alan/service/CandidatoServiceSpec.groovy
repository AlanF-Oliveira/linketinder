package org.alan.service

import org.alan.database.BancoDeDados
import org.alan.model.Candidato
import spock.lang.Specification

class CandidatoServiceSpec extends Specification {

    BancoDeDadosFalso bancoDeDados = new BancoDeDadosFalso()
    CandidatoService candidatoService = new CandidatoService(bancoDeDados)

    def "deve cadastrar candidato e retornar o registro com id"() {
        given:
        Candidato candidato = new Candidato(nome: "Alan", cpf: "123")
        bancoDeDados.idGerado = 10

        when:
        Candidato resultado = candidatoService.salvar(candidato)

        then:
        bancoDeDados.chamadas == 1
        resultado.is(candidato)
        resultado.id == 10
    }

    def "deve informar falha quando o cadastro não gerar id"() {
        given:
        Candidato candidato = new Candidato(nome: "Alan", cpf: "123")

        when:
        candidatoService.salvar(candidato)

        then:
        bancoDeDados.chamadas == 1
        Exception erro = thrown()
        erro.message == "Falha ao cadastrar candidato"
    }

    private static class BancoDeDadosFalso extends BancoDeDados {
        int chamadas
        int idGerado

        @Override
        int insertCandidato(Candidato candidato) {
            chamadas++
            if (idGerado > 0) {
                candidato.id = idGerado
            }
            return idGerado
        }
    }
}
