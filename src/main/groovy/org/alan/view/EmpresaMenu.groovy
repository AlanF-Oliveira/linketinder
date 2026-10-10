package org.alan.view

import org.alan.controller.EmpresaController
import org.alan.model.Empresa

class EmpresaMenu {

    private final EmpresaController empresaController
    Scanner sc = new Scanner(System.in)

    EmpresaMenu(EmpresaController empresaController) {
        this.empresaController = empresaController
    }

    void menu() {
        boolean voltar = false
        while (!voltar) {
            println()
            println "-- Empresas --"
            println "1) Listar"
            println "2) Cadastrar"
            println "3) Atualizar"
            println "4) Deletar"
            println "5) Voltar"
            int opcao = sc.nextInt()
            switch (opcao) {
                case 1: listar()
                    break
                case 2: cadastrar()
                    break
                case 3: atualizar()
                    break
                case 4: deletar()
                    break
                case 5: voltar = true
                    break
            }
        }
    }

    void listar() {
        println()
        println "Lista de Empresas: "
        empresaController.listarEmpresas().each { it ->
            println(it)
        }
        println ""
    }

    void cadastrar() {
        println()
        print "Nome: "
        sc.nextLine()
        try {
            String nome = sc.nextLine()
            print "Email: "
            String email = sc.nextLine()
            print "Cnpj: "
            String cnpj = sc.nextLine()
            print "Pais: "
            String pais = sc.nextLine()
            print "Estado: "
            String estado = sc.nextLine()
            print "Cidade: "
            String cidade = sc.nextLine()
            print "Cep: "
            String cep = sc.nextLine()
            print "Descricao: "
            String descricao = sc.nextLine()
            print "Senha (mínimo 6 caracteres): "
            String senha = sc.nextLine()

            Empresa empresa = new Empresa(
                    nome: nome,
                    email: email,
                    cnpj: cnpj,
                    pais: pais,
                    estado: estado,
                    cidade: cidade,
                    cep: cep,
                    descricao: descricao,
                    senha: senha
            )
            empresaController.salvar(empresa)
            println "Empresa cadastrada com sucesso"
        } catch (Exception e) {
            println "Erro ao cadastrar empresa: ${e.message}"
        }
        println()
    }

    void atualizar() {
        println()
        print "CNPJ da empresa a atualizar: "
        sc.nextLine()
        try {
            String cnpj = sc.nextLine()
            Empresa existente = empresaController.buscarPorCnpj(cnpj)

            print "Nome: "
            String nome = sc.nextLine()
            print "Email: "
            String email = sc.nextLine()
            print "Pais: "
            String pais = sc.nextLine()
            print "Estado: "
            String estado = sc.nextLine()
            print "Cidade: "
            String cidade = sc.nextLine()
            print "Cep: "
            String cep = sc.nextLine()
            print "Descricao: "
            String descricao = sc.nextLine()
            print "Senha (mínimo 6 caracteres): "
            String senha = sc.nextLine()

            Empresa empresa = new Empresa(
                    cnpj: cnpj,
                    nome: nome,
                    email: email,
                    pais: pais,
                    estado: estado,
                    cidade: cidade,
                    cep: cep,
                    descricao: descricao,
                    senha: senha
            )
            empresaController.atualizarEmpresa(empresa)
            println "Empresa atualizada com sucesso"
        } catch (Exception e) {
            println "Erro ao atualizar empresa: ${e.message}"
        }
        println()
    }

    void deletar() {
        println()
        print "CNPJ da empresa: "
        sc.nextLine()
        try {
            String cnpj = sc.nextLine()
            empresaController.deletarEmpresa(cnpj)
            println "Empresa deletada com sucesso"
        } catch (Exception e) {
            println "Erro ao deletar empresa: ${e.message}"
        }
        println()
    }
}


