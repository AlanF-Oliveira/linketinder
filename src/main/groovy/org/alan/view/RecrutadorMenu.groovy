package org.alan.view

import org.alan.controller.RecrutadorController
import org.alan.model.Recrutador
import org.alan.service.SessaoAutenticada

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecrutadorMenu {

    private final RecrutadorController recrutadorController
    private final SessaoAutenticada sessaoEmpresa
    private final Scanner sc = new Scanner(System.in)

    RecrutadorMenu(
            RecrutadorController recrutadorController,
            SessaoAutenticada sessaoEmpresa
    ) {
        this.recrutadorController = recrutadorController
        this.sessaoEmpresa = sessaoEmpresa
    }

    void menu() {
        if (!sessaoEmpresa.ehEmpresa()) {
            println "Apenas empresas autenticadas podem gerenciar recrutadores."
            return
        }

        boolean voltar = false
        while (!voltar) {
            println()
            println "-- Recrutadores da empresa --"
            println "1) Listar"
            println "2) Cadastrar"
            println "3) Atualizar"
            println "4) Remover"
            println "5) Voltar"
            int opcao = sc.nextInt()
            switch (opcao) {
                case 1: listar(); break
                case 2: cadastrar(); break
                case 3: atualizar(); break
                case 4: remover(); break
                case 5: voltar = true; break
            }
        }
    }

    private void listar() {
        recrutadorController.listarPorEmpresa(sessaoEmpresa.usuario.cnpj).each {
            println it
        }
    }

    private void cadastrar() {
        sc.nextLine()
        try {
            print "Nome: "; String nome = sc.nextLine()
            print "Área de atuação: "; String area = sc.nextLine()
            print "Descrição: "; String descricao = sc.nextLine()
            print "Email: "; String email = sc.nextLine()
            print "CPF: "; String cpf = sc.nextLine()
            print "Data de nascimento (dd/MM/yyyy): "
            LocalDate nascimento = LocalDate.parse(
                    sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            print "Senha (mínimo 6 caracteres): "; String senha = sc.nextLine()

            recrutadorController.salvar(new Recrutador(
                    nome: nome,
                    areaAtuacao: area,
                    descricao: descricao,
                    email: email,
                    cpf: cpf,
                    nascimento: nascimento,
                    senha: senha
            ), sessaoEmpresa.usuario.cnpj)
            println "Recrutador cadastrado com sucesso"
        } catch (Exception erro) {
            println "Erro ao cadastrar recrutador: ${erro.message}"
        }
    }

    private void atualizar() {
        sc.nextLine()
        try {
            print "CPF do recrutador: "; String cpf = sc.nextLine()
            Recrutador atual = recrutadorController.buscarPorCpfNaEmpresa(
                    cpf, sessaoEmpresa.usuario.cnpj
            )
            print "Nome: "; atual.nome = sc.nextLine()
            print "Área de atuação: "; atual.areaAtuacao = sc.nextLine()
            print "Descrição: "; atual.descricao = sc.nextLine()
            print "Email: "; atual.email = sc.nextLine()
            print "Senha (mínimo 6 caracteres): "; atual.senha = sc.nextLine()
            recrutadorController.atualizar(atual, sessaoEmpresa.usuario.cnpj)
            println "Recrutador atualizado com sucesso"
        } catch (Exception erro) {
            println "Erro ao atualizar recrutador: ${erro.message}"
        }
    }

    private void remover() {
        sc.nextLine()
        try {
            print "CPF do recrutador: "
            recrutadorController.deletar(sc.nextLine(), sessaoEmpresa.usuario.cnpj)
            println "Recrutador removido com sucesso"
        } catch (Exception erro) {
            println "Erro ao remover recrutador: ${erro.message}"
        }
    }
}
