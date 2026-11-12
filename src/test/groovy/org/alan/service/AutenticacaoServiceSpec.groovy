package org.alan.service

import org.alan.model.Candidato
import org.alan.model.Recrutador
import org.alan.repository.CandidatoRepository
import org.alan.repository.EmpresaRepository
import org.alan.repository.RecrutadorRepository
import spock.lang.Specification

class AutenticacaoServiceSpec extends Specification {

    def "autentica candidato pelo CPF e mantém sessão até logout"() {
        given:
        Candidato candidato = new Candidato(cpf: "111", senha: "segredo")
        CandidatoService candidatos = new CandidatoService(Stub(CandidatoRepository) {
            buscarCandidatoPorCpf("111") >> candidato
        })
        EmpresaService empresas = new EmpresaService(Stub(EmpresaRepository) {
            buscarPorCnpj(_) >> { throw new Exception("não encontrado") }
        })
        RecrutadorService recrutadores = new RecrutadorService(
                Stub(RecrutadorRepository) {
            buscarPorCpf(_) >> { throw new Exception("não encontrado") }
                },
                Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                }
        )
        AutenticacaoService autenticacao = new AutenticacaoService(
                candidatos, empresas, recrutadores
        )

        when:
        SessaoAutenticada sessao = autenticacao.entrar("111", "segredo")

        then:
        sessao.tipo == "CANDIDATO"
        autenticacao.obterSessao() == sessao

        when:
        autenticacao.sair()

        then:
        autenticacao.obterSessao() == null
    }

    def "recusa recrutador sem vínculo ativo"() {
        given:
        Recrutador recrutador = new Recrutador(
                cpf: "222", senha: "segredo", status: "INATIVO"
        )
        AutenticacaoService autenticacao = new AutenticacaoService(
                    new CandidatoService(Stub(CandidatoRepository)),
                    new EmpresaService(Stub(EmpresaRepository)),
                    new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf("222") >> recrutador
                    }, Stub(EmpresaRepository))
        )

        when:
        autenticacao.entrar("222", "segredo")

        then:
        def erro = thrown(Exception)
        erro.message == "Recrutador sem vínculo ativo"
    }

    def "recusa credenciais inválidas"() {
        given:
        AutenticacaoService autenticacao = new AutenticacaoService(
                new CandidatoService(Stub(CandidatoRepository)),
                new EmpresaService(Stub(EmpresaRepository)),
                new RecrutadorService(Stub(RecrutadorRepository), Stub(EmpresaRepository))
        )

        when:
        autenticacao.entrar("999", "errada")

        then:
        def erro = thrown(Exception)
        erro.message == "Credenciais inválidas"
    }
}
