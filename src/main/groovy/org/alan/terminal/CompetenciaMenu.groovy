package org.alan.terminal

import org.alan.service.CompetenciaService

class CompetenciaMenu {

    CompetenciaService competenciaService
    Scanner sc = new Scanner(System.in)

    CompetenciaMenu(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService
    }

    void menu() {
        boolean voltar = false
        while (!voltar) {
            println()
            println "-- Competências --"
            println "1) Listar"
            println "2) Atualizar"
            println "3) Deletar"
            println "4) Voltar"
            int opcao = sc.nextInt()
            switch (opcao) {
                case 1: listar(); break
                case 2: atualizar(); break
                case 3: deletar(); break
                case 4: voltar = true; break
            }
        }
    }

    void listar() {
        println()
        println "Lista de Competências: "
        competenciaService.listarCompetencias().each {
            println(it)
        }
        println ""
    }

    void atualizar() {
        println()
        print "Id da competência a atualizar: "
        try {
            int id = sc.nextInt()
            sc.nextLine()
            print "Novo nome: "
            String novoNome = sc.nextLine()
            competenciaService.atualizarCompetencia(novoNome, id)
            println "Competência atualizada com sucesso"
        } catch (Exception e) {
            println "Erro ao atualizar competência: ${e.message}"
        }
        println()
    }

    void deletar() {
        println()
        print "Id da competência a deletar: "
        try {
            int id = sc.nextInt()
            competenciaService.deletarCompetencia(id)
            println "Competência deletada com sucesso"
        } catch (Exception e) {
            println "Erro ao deletar competência: ${e.message}"
        }
        println()
    }
}