package org.alan.terminal

import org.alan.model.Candidato
import org.alan.service.CandidatoService

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CandidatoMenu {
    CandidatoService candidatoService
    Scanner sc = new Scanner(System.in)

    CandidatoMenu(CandidatoService candidatoService) {
        this.candidatoService = candidatoService

    }

    void menu() {
        boolean voltar = false
        while (!voltar) {
            println()
            println "-- Candidatos --"
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
        println "Lista de Candidatos: "
        candidatoService.listarCandidatos().each { candidato ->
            println(candidato.toString())
        }
        println ""
    }

    void cadastrar() {
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

    void atualizar() {
        println()
        print "CPF do candidato a atualizar: "
        sc.nextLine()
        try {
            String cpf = sc.nextLine()
            Candidato existente = candidatoService.buscarPorCpf(cpf)

            print "Nome: "
            String nome = sc.nextLine()
            print "Sobrenome: "
            String sobrenome = sc.nextLine()
            print "Data de nascimento (dd/MM/yyyy): "
            String nascimentoStr = sc.nextLine()
            LocalDate nascimento = LocalDate.parse(nascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            print "Email: "
            String email = sc.nextLine()
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

            Candidato candidato = new Candidato(
                    cpf: cpf,
                    nome: nome,
                    sobrenome: sobrenome,
                    nascimento: nascimento,
                    email: email,
                    descricao: descricao,
                    pais: pais,
                    estado: estado,
                    cidade: cidade,
                    cep: cep,
                    senha: senha
            )
            candidatoService.atualizarCandidato(candidato)
            println "Candidato atualizado com sucesso"
        } catch (Exception e) {
            println "Erro ao atualizar candidato: ${e.message}"
        }
        println()
    }

    void deletar() {
        println()
        print "CPF do candidato: "
        sc.nextLine()
        try {
            String cpf = sc.nextLine()
            candidatoService.deletarCandidato(cpf)
            println "Candidato deletado com sucesso"
        } catch (Exception e) {
            println "Erro ao deletar candidato: ${e.message}"
        }
        println()
    }
}