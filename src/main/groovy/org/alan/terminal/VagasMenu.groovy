package org.alan.terminal

import org.alan.model.Empresa
import org.alan.model.Vaga
import org.alan.service.EmpresaService
import org.alan.service.VagaService

class VagasMenu {

    VagaService vagaService
    EmpresaService empresaService
    Scanner sc = new Scanner(System.in)

    VagasMenu(VagaService vagaService, EmpresaService empresaService) {
        this.vagaService = vagaService
        this.empresaService = empresaService
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
        vagaService.listarVagas().each {
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
            Empresa empresa = empresaService.buscarPorCnpj(cnpj)
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
            List<String> competencias = competenciasSc.split(",")*.trim()
            Vaga vaga = new Vaga(
                    titulo: titulo,
                    descricao: descricao,
                    estado: estado,
                    cidade: cidade,
                    competenciasExigidas: competencias
            )
            vagaService.criarVaga(vaga, empresa.id)
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
            Vaga existente = vagaService.buscarVagaPorId(id)
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
            vagaService.atualizarVaga(vaga)
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
            vagaService.deletarVaga(id)
            println "Vaga deletada com sucesso"
        } catch (Exception e) {
            println "Erro ao deletar vaga: ${e.message}"
        }
        println()
    }
}