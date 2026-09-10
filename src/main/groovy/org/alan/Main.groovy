package org.alan

import org.alan.database.BancoDeDados
import org.alan.terminal.Menu

static void main(String[] args) {

    BancoDeDados bd = new BancoDeDados()
    Menu menu = new Menu()
    menu.menu()
}