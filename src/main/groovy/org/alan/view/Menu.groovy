package org.alan.view

import org.alan.controller.CandidatoController
import org.alan.controller.CompetenciaController
import org.alan.controller.EmpresaController
import org.alan.controller.VagaController
import org.alan.dao.CandidatoDAO
import org.alan.dao.CompetenciaDAO
import org.alan.dao.EmpresaDAO
import org.alan.dao.VagaDAO
import org.alan.database.ConnectionFactory
import org.alan.database.PostgresConnectionFactory
import org.alan.service.CandidatoService
import org.alan.service.CompetenciaService
import org.alan.service.EmpresaService
import org.alan.service.VagaService

class Menu {

    ConnectionFactory connectionFactory = new PostgresConnectionFactory()
    CompetenciaDAO competenciaDAO = new CompetenciaDAO(connectionFactory)
    CandidatoDAO candidatoDAO = new CandidatoDAO(connectionFactory, competenciaDAO)
    EmpresaDAO empresaDAO = new EmpresaDAO(connectionFactory)
    VagaDAO vagaDAO = new VagaDAO(connectionFactory, competenciaDAO, empresaDAO)
    CandidatoService candidatoService = new CandidatoService(candidatoDAO)
    CandidatoController candidatoController = new CandidatoController(candidatoService)
    CandidatoMenu candidatoMenu = new CandidatoMenu(candidatoController)
    EmpresaService empresaService = new EmpresaService(empresaDAO)
    EmpresaController empresaController = new EmpresaController(empresaService)
    EmpresaMenu empresaMenu = new EmpresaMenu(empresaController)
    VagaService vagaService = new VagaService(vagaDAO)
    VagaController vagaController = new VagaController(vagaService, empresaService)
    VagasMenu vagasMenu = new VagasMenu(vagaController)
    CompetenciaService competenciaService = new CompetenciaService(competenciaDAO)
    CompetenciaController competenciaController = new CompetenciaController(competenciaService)
    CompetenciaMenu competenciaMenu = new CompetenciaMenu(competenciaController)
    Scanner sc = new Scanner(System.in)

    void menu() {

        println "Bem vindo ao Linketinder"
        def opcao;
        boolean isActive = true;
        while (isActive) {
            println "O que deseja fazer?"
            println "1) Área do candidato"
            println "2) Área da empresa"
            println "3) Área de Vagas"
            println "4) Área das Competências"
            println "5) Sair"
            opcao = sc.nextInt()
            switch (opcao) {
                case 1:
                    candidatoMenu.menu()
                    break
                case 2:
                    empresaMenu.menu()
                    break
                case 3:
                    vagasMenu.menu()
                    break
                case 4:
                    competenciaMenu.menu()
                    break
                case 5:
                    isActive = false
                    break
            }
        }
        sc.close()
    }
}