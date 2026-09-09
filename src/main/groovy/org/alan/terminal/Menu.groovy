package org.alan.terminal

import org.alan.database.BancoDeDados
import org.alan.model.Candidato
import org.alan.model.Empresa
import org.alan.model.Vaga
import org.alan.service.CandidatoService
import org.alan.service.EmpresaService
import org.alan.service.VagaService

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Menu {
    BancoDeDados bd = new BancoDeDados()
    //UsuariosCadastrados usuariosCadastrados = new UsuariosCadastrados();
    //VagasCadastradas vagasCadastradas = new VagasCad65astradas(usuariosCadastrados.empresas);
    CandidatoService candidatoService = new CandidatoService(bd)
    EmpresaService empresaService = new EmpresaService(bd)
    VagaService vagaService = new VagaService(bd)
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
            println "5) Cadastrar Vagas"
            println "6) Listar Vagas"
            println "7) Sair"
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
                    cadastrarVaga()
                    break
                case 6:
                    listarVagas()
                    break
                case 7:
                    isActive = false;
                    break
            }
        }
        sc.close()
    }

    void listarCandidatos() {
        println()
        println "Lista de Candidatos: "
        candidatoService.listarCandidatos().each { candidato ->
            println(candidato.toString())
        }
        println ""
    }

    void listarEmpresas() {
        println()
        println "Lista de Empresas: "
        empresaService.listarEmpresas().each { empresa ->
            println(empresa.toString())
        }
        println ""
    }

    void cadastrarCandidato() {
        println()
        print "Nome: "
        try {
            sc.nextLine()
            String nome = sc.nextLine()
            print "Sobrenome: "
            String sobrenome = sc.nextLine()
            print "Data de nascimento (dd/MM/yyyy): "
            String nascimentoStr = sc.nextLine()
            LocalDate nascimento = LocalDate.parse(nascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            print "Email: "
            String email = sc.nextLine()
            print "CPF: "
            String cpf = sc.nextLine()
            print "Descrição: "
            String descricao = sc.nextLine()
            print "País: "
            String pais = sc.nextLine()
            print "Estado: "
            String estado = sc.nextLine()
            print "Cidade: "
            String cidade = sc.nextLine()
            print "Cep: "
            String cep = sc.nextLine()
            print "Senha (mínimo 6 caracteres): "
            String senha = sc.nextLine()
            print "Competências (separadas por vírgula): "
            String competenciasSc = sc.nextLine()
            List<String> competencias = competenciasSc.split(",")*.trim()

            Candidato candidato = new Candidato(
                    nome: nome,
                    sobrenome: sobrenome,
                    nascimento: nascimento,
                    email: email,
                    cpf: cpf,
                    descricao: descricao,
                    pais: pais,
                    estado: estado,
                    cidade: cidade,
                    cep: cep,
                    senha: senha,
                    competencias: competencias
            )
            candidatoService.salvar(candidato)
            println "Candidato cadastrado com sucesso"
        } catch (Exception e) {
            println "Erro ao cadastrar candidato: ${e.message}"
        }
        println()
    }

    void cadastrarEmpresa() {
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
            empresaService.salvar(empresa)
            println "Empresa cadastrada com sucesso"
        } catch (Exception e) {
            println "Erro ao cadastrar empresa: ${e.message}"
        }
        println()
    }

    void listarVagas() {
        println ""
        println "Lista de Vagas: "
        vagaService.listarVagas().each {
            println(it)
        }
        println ""
    }

    void cadastrarVaga() {
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
}

