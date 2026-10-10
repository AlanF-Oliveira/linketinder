package org.alan.view


import org.alan.controller.VagaController
import org.alan.model.Vaga

class VagasMenu {
    private final VagaController vagaController
    Scanner sc = new Scanner(System.in)

    VagasMenu(VagaController vagaController) {
        this.vagaController = vagaController
    }

    void menu() {
        boolean voltar = false
        while (!voltar) {
            println()
            println "-- Vagas --"
            println "1) Listar"
            println "2) Cadastrar"
            println "3) Atualizar"
            println "4) Deletar"
            println "5) Voltar"
            int opcao = sc.nextInt()
            switch (opcao) {
                case 1: listar();
                    break
                case 2: cadastrar();
                    break
                case 3: atualizar();
                    break
                case 4: deletar();
                    break
                case 5: voltar = true;
                    break
            }
        }
    }

    void listar() {
        println()
        println "Lista de Vagas: "
        vagaController.listarVagas().each {
            println(it)
        }
        println ""
    }

    void cadastrar() {
        println()
        print "CNPJ da empresa: "
        sc.nextLine()
        try {
            String cnpj = sc.nextLine()
            print "Título da vaga: "
            String titulo = sc.nextLine()
            print "Descrição: "
            String descricao = sc.nextLine()
            print "Estado: "
            String estado = sc.nextLine()
            print "Cidade: "
            String cidade = sc.nextLine()
            print "Competências exigidas (separadas por vírgula): "
            String competenciasSc = sc.nextLine()
            List<String> competencias = competenciasSc.tokenize(",")*.trim()
            Vaga vaga = new Vaga(
                    titulo: titulo,
                    descricao: descricao,
                    estado: estado,
                    cidade: cidade,
                    competenciasExigidas: competencias
            )
            vagaController.criarVaga(vaga, cnpj)
            println "Vaga cadastrada com sucesso"
        } catch (Exception e) {
            println "Erro ao cadastrar vaga: ${e.message}"
        }
        println()
    }

    void atualizar() {
        println()
        print "Id da vaga a atualizar: "
        try {
            int id = sc.nextInt()
            sc.nextLine()
            Vaga existente = vagaController.buscarVagaPorId(id)
            print "Título: "
            String titulo = sc.nextLine()
            print "Descrição: "
            String descricao = sc.nextLine()
            print "Estado: "
            String estado = sc.nextLine()
            print "Cidade: "
            String cidade = sc.nextLine()
            print "Competências exigidas (separadas por vírgula): "
            String competenciasSc = sc.nextLine()
            List<String> competencias = competenciasSc.split(",")*.trim()
            Vaga vaga = new Vaga(
                    id: id,
                    titulo: titulo,
                    descricao: descricao,
                    estado: estado,
                    cidade: cidade,
                    competenciasExigidas: competencias
            )
            vagaController.atualizarVaga(vaga)
            println "Vaga atualizada com sucesso"
        } catch (Exception e) {
            println "Erro ao atualizar vaga: ${e.message}"
        }
        println()
    }

    void deletar() {
        println()
        print "Id da vaga: "
        try {
            int id = sc.nextInt()
            vagaController.deletarVaga(id)
            println "Vaga deletada com sucesso"
        } catch (Exception e) {
            println "Erro ao deletar vaga: ${e.message}"
        }
        println()
    }
}