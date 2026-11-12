package org.alan.view

import org.alan.controller.CandidatoController
import org.alan.controller.CompetenciaController
import org.alan.controller.EmpresaController
import org.alan.controller.VagaController
import org.alan.dao.CandidatoDAO
import org.alan.dao.CompetenciaDAO
import org.alan.dao.EmpresaDAO
import org.alan.dao.MatchDAO
import org.alan.dao.VagaDAO
import org.alan.database.ConnectionFactory
import org.alan.database.PostgresConnectionFactory
import org.alan.service.CandidatoService
import org.alan.service.CompetenciaService
import org.alan.service.EmpresaService
import org.alan.service.MatchService
import org.alan.service.VagaService
import org.alan.controller.MatchController
import org.alan.dao.RecrutadorDAO
import org.alan.controller.RecrutadorController
import org.alan.service.RecrutadorService
import org.alan.service.AutenticacaoService
import org.alan.service.SessaoAutenticada

class Menu {

    ConnectionFactory connectionFactory = new PostgresConnectionFactory()
    CompetenciaDAO competenciaDAO = new CompetenciaDAO(connectionFactory)
    CandidatoDAO candidatoDAO = new CandidatoDAO(connectionFactory, competenciaDAO)
    EmpresaDAO empresaDAO = new EmpresaDAO(connectionFactory)
    VagaDAO vagaDAO = new VagaDAO(connectionFactory, competenciaDAO, empresaDAO)
    CandidatoService candidatoService = new CandidatoService(candidatoDAO)
    CandidatoController candidatoController = new CandidatoController(candidatoService)
    EmpresaService empresaService = new EmpresaService(empresaDAO)
    EmpresaController empresaController = new EmpresaController(empresaService)
    RecrutadorDAO recrutadorDAO = new RecrutadorDAO(connectionFactory, empresaDAO)
    RecrutadorService recrutadorService = new RecrutadorService(recrutadorDAO, empresaDAO)
    RecrutadorController recrutadorController = new RecrutadorController(recrutadorService)
    MatchDAO matchDAO = new MatchDAO(connectionFactory)
    MatchService matchService = new MatchService(matchDAO, recrutadorDAO)
    MatchController matchController = new MatchController(matchService)
    MatchMenu matchMenu = new MatchMenu(matchController)
    CandidatoMenu candidatoMenu = new CandidatoMenu(candidatoController, matchMenu)
    EmpresaMenu empresaMenu = new EmpresaMenu(empresaController, matchMenu)
    VagaService vagaService = new VagaService(vagaDAO)
    VagaController vagaController = new VagaController(vagaService, empresaService)
    VagasMenu vagasMenu = new VagasMenu(vagaController)
    CompetenciaService competenciaService = new CompetenciaService(competenciaDAO)
    CompetenciaController competenciaController = new CompetenciaController(competenciaService)
    CompetenciaMenu competenciaMenu = new CompetenciaMenu(competenciaController)
    AutenticacaoService autenticacaoService = new AutenticacaoService(
            candidatoService, empresaService, recrutadorService
    )
    Scanner sc = new Scanner(System.in)

    void menu() {

        println "Bem vindo ao Linketinder"
        boolean isActive = true
        while (isActive) {
            println "O que deseja fazer?"
            println "1) Entrar"
            println "2) Cadastros"
            println "3) Sair"
            int opcao = sc.nextInt()
            switch (opcao) {
                case 1:
                    entrar()
                    break
                case 2:
                    cadastros()
                    break
                case 3:
                    isActive = false
                    break
            }
        }
        sc.close()
    }

    private void entrar() {
        sc.nextLine()
        print "CPF ou CNPJ: "
        String identificador = sc.nextLine()
        print "Senha: "
        String senha = sc.nextLine()
        try {
            SessaoAutenticada sessao = autenticacaoService.entrar(identificador, senha)
            println "Login realizado como ${sessao.tipo.toLowerCase()}."
            areaAutenticada(sessao)
            autenticacaoService.sair()
            println "Sessão encerrada."
        } catch (Exception erro) {
            println "Erro ao entrar: ${erro.message}"
        }
    }

    private void areaAutenticada(SessaoAutenticada sessao) {
        switch (sessao.tipo) {
            case "CANDIDATO":
                candidatoMenu.menu()
                break
            case "EMPRESA":
                empresaMenu.menu()
                new RecrutadorMenu(
                        recrutadorController, sessao, vagaController, matchController
                ).menu()
                break
            case "RECRUTADOR":
                new VagasMenu(vagaController, sessao.usuario.empresa.cnpj).menu()
                break
        }
    }

    private void cadastros() {
        println()
        println "-- Cadastros --"
        println "1) Cadastrar candidato"
        println "2) Cadastrar empresa"
        println "3) Voltar"
        int opcao = sc.nextInt()
        switch (opcao) {
            case 1:
                candidatoMenu.cadastrar()
                break
            case 2:
                empresaMenu.cadastrar()
                break
        }
    }
}