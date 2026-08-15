package org.alan.Terminal

import org.alan.usuarios.UsuariosCadastrados

class Menu {
    UsuariosCadastrados usuariosCadastrados = new UsuariosCadastrados();

    void menu() {
        Scanner sc = new Scanner(System.in)
        println "Bem vindo ao Linketinder"
        def opcao;
        boolean isActive = true;
        while (isActive) {
            println "O que deseja fazer?"
            println "1) Listar candidatos"
            println "2) Listar Empresas"
            println "3) Sair"
            opcao = sc.nextInt()
            switch (opcao) {
                case 1:
                    listarCandidatos()
                    break
                case 2:
                    listarEmpresas()
                    break
                case 3:
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
        println"Lista de Empresas: "
        usuariosCadastrados.empresas.each { empresa ->
            println(empresa.toString())
        }
        println ""
    }
}

