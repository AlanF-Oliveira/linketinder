package org.alan.terminal

import org.alan.dao.CandidatoDAO
import org.alan.dao.CompetenciaDAO
import org.alan.dao.EmpresaDAO
import org.alan.dao.VagaDAO
import org.alan.database.BancoDeDados
import org.alan.service.CandidatoService
import org.alan.service.CompetenciaService
import org.alan.service.EmpresaService
import org.alan.service.VagaService

class Menu {
    BancoDeDados bd = new BancoDeDados()
    CandidatoDAO candidatoDAO = new CandidatoDAO()
    CompetenciaDAO competenciaDAO = new CompetenciaDAO()
    EmpresaDAO empresaDAO = new EmpresaDAO()
    VagaDAO vagaDAO = new VagaDAO()
    CandidatoService candidatoService = new CandidatoService(candidatoDAO)
    EmpresaService empresaService = new EmpresaService(empresaDAO)
    VagaService vagaService = new VagaService(vagaDAO)
    CompetenciaService competenciaService = new CompetenciaService(competenciaDAO)
    CandidatoMenu candidatoMenu = new CandidatoMenu(candidatoService)
    EmpresaMenu empresaMenu = new EmpresaMenu(empresaService)
    VagasMenu vagasMenu = new VagasMenu(vagaService, empresaService)
    CompetenciaMenu competenciaMenu = new CompetenciaMenu(competenciaService)
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