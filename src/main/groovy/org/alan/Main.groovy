package org.alan

import org.alan.database.BancoDeDados
import org.alan.service.CompetenciaService
import org.alan.terminal.Menu

static void main(String[] args) {

    BancoDeDados bd = new BancoDeDados()
    Menu menu = new Menu()
    menu.menu()
//    CompetenciaService competenciaService = new CompetenciaService(bd);
//    competenciaService.listarCompetencias().forEach {
//        println(it)
//    }




}