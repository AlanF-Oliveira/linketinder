package org.alan.view

import org.alan.controller.MatchController
import org.alan.model.Match
import org.alan.service.SessaoAutenticada

class MatchMenu {

    private final MatchController matchController
    private final SessaoAutenticada sessao
    Scanner sc = new Scanner(System.in)

    MatchMenu(MatchController matchController) {
        this.matchController = matchController
        this.sessao = null
    }

    MatchMenu(MatchController matchController, SessaoAutenticada sessao) {
        this.matchController = matchController
        this.sessao = sessao
    }

    void curtirVaga() {
        println()
        print "Id do candidato: "
        int idCandidato = sc.nextInt()
        print "Id da vaga: "
        int idVaga = sc.nextInt()
        try {
            List<Match> matches = matchController.curtirVaga(idCandidato, idVaga)
            println "Curtida registrada."
            exibirMatchesCriados(matches)
        } catch (Exception erro) {
            println "Erro ao curtir vaga: ${erro.message}"
        }
    }

    void curtirCandidato() {
        println()
        print "Id da empresa: "
        int idEmpresa = sc.nextInt()
        print "Id do candidato: "
        int idCandidato = sc.nextInt()
        try {
            List<Match> matches =
                    matchController.curtirCandidato(idEmpresa, idCandidato)
            println "Curtida registrada."
            exibirMatchesCriados(matches)
        } catch (Exception erro) {
            println "Erro ao curtir candidato: ${erro.message}"
        }
    }

    void curtirCandidatoComoRecrutador() {
            if (!sessao?.ehRecrutador()) {
                println "Apenas recrutadores autenticados podem usar esta operação."
                return
            }
            println()
            print "Id do candidato: "
            int idCandidato = sc.nextInt()
            try {
                List<Match> matches = matchController.curtirCandidatoComoRecrutador(
                        sessao.usuario.empresa.id,
                        idCandidato,
                        sessao.usuario.id,
                        sessao.usuario.cpf,
                        sessao.usuario.empresa.cnpj
                )
                println "Curtida registrada em nome da empresa."
                exibirMatchesCriados(matches)
            } catch (Exception erro) {
                println "Erro ao curtir candidato: ${erro.message}"
        }
    }

    void listarMatchesDoCandidato() {
        println()
        print "Id do candidato: "
        int idCandidato = sc.nextInt()
        exibir(matchController.listarPorCandidato(idCandidato))
    }

    void listarMatchesDaEmpresa() {
        println()
        print "Id da empresa: "
        int idEmpresa = sc.nextInt()
        exibir(matchController.listarPorEmpresa(idEmpresa))
    }

    void listarMatchesDaEmpresaAutenticada() {
        if (!sessao?.ehRecrutador()) {
            println "Apenas recrutadores autenticados podem consultar esta operação."
            return
        }
        exibir(matchController.listarPorEmpresa(sessao.usuario.empresa.id))
    }

    private static void exibir(List<Match> matches) {
        println()
        if (matches.isEmpty()) {
            println "Nenhum match encontrado."
        } else {
            println "Matches:"
            matches.each { match ->
                println "Vaga: ${match.nomeDaVaga}"
                println "Descrição da vaga: ${match.descricaoDaVaga}"
                println "Local da vaga: ${match.localDaVaga}"
                println "Candidato: ${match.nomeCandidato}"
                println "E-mail do candidato: ${match.emailCandidato}"
                println "Descrição do candidato: ${match.descricaoDoCandidato}"
                println "Local do candidato: ${match.localDoCandidato}"
                println "Empresa: ${match.nomeEmpresa}"
                println "E-mail da empresa: ${match.emailEmpresa}"
                println "Descrição da empresa: ${match.descricaoDaEmpresa}"
                println "Local da empresa: ${match.localDaEmpresa}"
                println "Id da vaga: ${match.idVaga}"
                println "Id do candidato: ${match.idCandidato}"
                println()
            }
        }
    }

    private static void exibirMatchesCriados(List<Match> matches) {
        if (matches.isEmpty()) {
            println "Ainda não existe match para essa curtida."
        } else {
            println "Match criado:"
            exibir(matches)
        }
    }
}
