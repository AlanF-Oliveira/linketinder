package org.alan.terminal

import org.alan.database.BancoDeDados
import org.alan.service.CandidatoService
import org.alan.service.CompetenciaService
import org.alan.service.EmpresaService
import org.alan.service.VagaService

class Menu {
    BancoDeDados bd = new BancoDeDados()
    CandidatoService candidatoService = new CandidatoService(bd)
    EmpresaService empresaService = new EmpresaService(bd)
    VagaService vagaService = new VagaService(bd)
    CompetenciaService competenciaService = new CompetenciaService(bd)
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