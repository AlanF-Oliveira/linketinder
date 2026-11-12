package org.alan.view

import org.alan.controller.RecrutadorController
import org.alan.controller.VagaController
import org.alan.controller.MatchController
import org.alan.controller.CandidatoController
import org.alan.model.Candidato
import org.alan.model.Recrutador
import org.alan.service.SessaoAutenticada

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecrutadorMenu {

    private final RecrutadorController recrutadorController
    private final VagasMenu vagasMenu
    private final MatchMenu matchMenu
    private final SessaoAutenticada sessaoEmpresa
    private final Scanner sc = new Scanner(System.in)

    RecrutadorMenu(
            RecrutadorController recrutadorController,
            SessaoAutenticada sessaoEmpresa,
            VagaController vagaController,
            MatchController matchController
    ) {
        this.recrutadorController = recrutadorController
        this.sessaoEmpresa = sessaoEmpresa
        String cnpjEmpresa = sessaoEmpresa.ehEmpresa()
                ? sessaoEmpresa.usuario.cnpj
                : sessaoEmpresa.usuario.empresa.cnpj
        this.vagasMenu = new VagasMenu(vagaController, cnpjEmpresa)
        this.matchMenu = new MatchMenu(matchController, sessaoEmpresa)
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

    void menuAutenticado(SessaoAutenticada sessao, CandidatoController candidatoController) {
            boolean voltar = false
            while (!voltar) {
                println()
                println "-- Área do recrutador --"
                println "1) Visualizar candidatos"
                println "2) Curtir candidato"
                println "3) Visualizar matches"
                println "4) Gerenciar vagas"
                println "5) Sair"
                int opcao = sc.nextInt()
                switch (opcao) {
                    case 1: listarCandidatosAnonimos(candidatoController); break
                    case 2: matchMenu.curtirCandidatoComoRecrutador(); break
                    case 3: matchMenu.listarMatchesDaEmpresaAutenticada(); break
                    case 4: vagasMenu.menu(); break
                    case 5: voltar = true; break
                }
            }
        }

    private void listarCandidatosAnonimos(CandidatoController candidatoController) {
        println()
        println "Candidatos disponíveis:"
        candidatoController.listarCandidatos().each { Candidato candidato ->
            println "Id: ${candidato.id}"
            println "Competências: ${candidato.competencias}"
            println "Descrição: ${candidato.descricao}"
            println "Local: ${candidato.cidade}, ${candidato.estado}, ${candidato.pais}"
            println()
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
