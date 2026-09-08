package org.alan

import org.alan.database.BancoDeDados
import org.alan.model.Candidato
import org.alan.terminal.Menu
import org.alan.usuarios.UsuariosCadastrados

static void main(String[] args) {
   BancoDeDados bd = new BancoDeDados()
//    UsuariosCadastrados usuariosCadastrados = new UsuariosCadastrados();
//    bd.insertCandidato(usuariosCadastrados.candidatos[1])
    Candidato c = bd.buscarCandidatoPorCpf("10403796542")
    println c
    Menu menu = new Menu();
    menu.menu()

}