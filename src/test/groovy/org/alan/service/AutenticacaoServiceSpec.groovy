package org.alan.service

import org.alan.model.Candidato
import org.alan.model.Empresa
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
                    new CandidatoService(Stub(CandidatoRepository) {
                        buscarCandidatoPorCpf(_) >> { throw new Exception("não encontrado") }
                    }),
                    new EmpresaService(Stub(EmpresaRepository) {
                        buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                    }),
                    new RecrutadorService(Stub(RecrutadorRepository) {
                        buscarPorCpf("222") >> recrutador
                    }, Stub(EmpresaRepository) {
                        buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                    })
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
                new CandidatoService(Stub(CandidatoRepository) {
                    buscarCandidatoPorCpf(_) >> { throw new Exception("não encontrado") }
                }),
                new EmpresaService(Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                }),
                new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf(_) >> { throw new Exception("não encontrado") }
                }, Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                })
        )

        when:
        autenticacao.entrar("999", "errada")

        then:
        def erro = thrown(Exception)
        erro.message == "Credenciais inválidas"
    }

    def "recusa CPF cadastrado como candidato e recrutador mesmo com senhas diferentes"() {
        given:
        Candidato candidato = new Candidato(cpf: "999", senha: "candidato")
        Recrutador recrutador = new Recrutador(
                cpf: "999", senha: "recrutador", status: "ATIVO"
        )
        AutenticacaoService autenticacao = new AutenticacaoService(
                new CandidatoService(Stub(CandidatoRepository) {
                    buscarCandidatoPorCpf("999") >> candidato
                }),
                new EmpresaService(Stub(EmpresaRepository)),
                new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf("999") >> recrutador
                }, Stub(EmpresaRepository))
        )

        when:
        autenticacao.entrar("999", "candidato")

        then:
        def erro = thrown(Exception)
        erro.message == "CPF cadastrado para mais de um tipo de usuário"
    }

    def "autentica empresa por CNPJ"() {
        given:
        Empresa empresa = new Empresa(cnpj: "123", senha: "senha123")
        AutenticacaoService autenticacao = new AutenticacaoService(
                new CandidatoService(Stub(CandidatoRepository) {
                    buscarCandidatoPorCpf(_) >> { throw new Exception("não encontrado") }
                }),
                new EmpresaService(Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj("123") >> empresa
                }),
                new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf(_) >> { throw new Exception("não encontrado") }
                }, Stub(EmpresaRepository))
        )

        when:
        SessaoAutenticada sessao = autenticacao.entrar("123", "senha123")

        then:
        sessao.tipo == "EMPRESA"
        sessao.usuario.is(empresa)
    }

    def "autentica recrutador com vínculo ativo"() {
        given:
        Recrutador recrutador = new Recrutador(
                cpf: "555", senha: "senha123", status: "ATIVO"
        )
        AutenticacaoService autenticacao = new AutenticacaoService(
                new CandidatoService(Stub(CandidatoRepository) {
                    buscarCandidatoPorCpf(_) >> { throw new Exception("não encontrado") }
                }),
                new EmpresaService(Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj(_) >> { throw new Exception("não encontrado") }
                }),
                new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf("555") >> recrutador
                }, Stub(EmpresaRepository))
        )

        when:
        SessaoAutenticada sessao = autenticacao.entrar("555", "senha123")

        then:
        sessao.tipo == "RECRUTADOR"
        sessao.usuario.is(recrutador)
    }

    def "recusa credenciais ambíguas com a mesma senha"() {
        given:
        Candidato candidato = new Candidato(cpf: "999", senha: "senha123")
        Empresa empresa = new Empresa(cnpj: "999", senha: "senha123")
        AutenticacaoService autenticacao = new AutenticacaoService(
                new CandidatoService(Stub(CandidatoRepository) {
                    buscarCandidatoPorCpf("999") >> candidato
                }),
                new EmpresaService(Stub(EmpresaRepository) {
                    buscarEmpresaPorCnpj("999") >> empresa
                }),
                new RecrutadorService(Stub(RecrutadorRepository) {
                    buscarPorCpf(_) >> { throw new Exception("não encontrado") }
                }, Stub(EmpresaRepository))
        )

        when:
        autenticacao.entrar("999", "senha123")

        then:
        Exception erro = thrown()
        erro.message == "Credenciais ambíguas para mais de um tipo de usuário"
    }
}
