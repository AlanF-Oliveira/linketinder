package org.alan.terminal

import org.alan.model.Candidato
import org.alan.model.Empresa
import org.alan.service.CandidatoService
import org.alan.service.EmpresaService
import org.alan.usuarios.UsuariosCadastrados

class Menu {
    UsuariosCadastrados usuariosCadastrados = new UsuariosCadastrados();
    CandidatoService candidatoService = new CandidatoService(usuariosCadastrados.candidatos)
    EmpresaService empresaService = new EmpresaService(usuariosCadastrados.empresas)
    Scanner sc = new Scanner(System.in)

    void menu() {

        println "Bem vindo ao Linketinder"
        def opcao;
        boolean isActive = true;
        while (isActive) {
            println "O que deseja fazer?"
            println "1) Listar candidatos"
            println "2) Listar Empresas"
            println "3) Cadastrar candidato"
            println "4) Cadastrar empresa"
            println "5) Sair"
            opcao = sc.nextInt()
            switch (opcao) {
                case 1:
                    listarCandidatos()
                    break
                case 2:
                    listarEmpresas()
                    break
                case 3:
                    cadastrarCandidato()
                    break
                case 4:
                    cadastrarEmpresa()
                    break
                case 5:
                    isActive = false;
                    break
            }
        }
        sc.close()
    }

    void listarCandidatos() {
        println()
        println "Lista de Candidatos: "
        usuariosCadastrados.candidatos.each { candidato ->
            println(candidato.toString())
        }
        println ""
    }

    void listarEmpresas() {
        println()
        println "Lista de Empresas: "
        usuariosCadastrados.empresas.each { empresa ->
            println(empresa.toString())
        }
        println ""
    }

    void cadastrarCandidato() {
        println()
        //  $nome | $idade | $email | $cpf | $descricao | $estado | $cep | $competencias"
        print "Nome: "
        sc.nextLine()
        String nome = sc.nextLine()
        print "Idade: "
        int idade = sc.nextInt()
        print "Email: "
        sc.nextLine()
        String email = sc.nextLine()
        print "cpf: "
        String cpf = sc.nextLine()
        print "Descrição: "
        String descricao = sc.nextLine()
        print "Estado: "
        String estado = sc.nextLine()
        print "Cep: "
        String cep = sc.nextLine()
        print "Competências (separadas por vírgula): "
        String competenciasSc = sc.nextLine()
        List<String> competencias = competenciasSc.split(",")*.trim()
        Candidato candidato = new Candidato(
                nome: nome,
                idade: idade,
                email: email,
                cpf: cpf,
                descricao: descricao,
                estado: estado,
                cep: cep,
                competencias: competencias
        )
        candidatoService.salvar(candidato)
        println "Candidato cadastrado com sucesso"
        println()
    }

    void cadastrarEmpresa(){
        println()
        //  "$nome | $email | $cnpj | $pais | $estado | $cep | $descricao | $competenciasDesejadas"
        print "Nome: "
        sc.nextLine()
        String nome = sc.nextLine()
        print "Email: "
        String email = sc.nextLine()
        print "Cnpj : "
        String cnpj  = sc.nextLine()
        print "Pais: "
        String pais = sc.nextLine()
        print "Estado: "
        String estado = sc.nextLine()
        print "Cep: "
        String cep = sc.nextLine()
        print "Descricao: "
        String descricao = sc.nextLine()
        print "Competências desejadas (separadas por vírgula): "
        String competenciasSc = sc.nextLine()
        List<String> competencias = competenciasSc.split(",")*.trim()
        Empresa empresa = new Empresa(
                nome: nome,
                email: email,
                cnpj: cnpj,
                pais: pais,
                estado: estado,
                cep: cep,
                descricao: descricao,
                competenciasDesejadas: competencias
        )
        empresaService.salvar(empresa)
        println "Empresa cadastrada com sucesso"
        println()
    }
}

