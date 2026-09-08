package org.alan.database

import java.sql.Connection

class BancoDeDados {

    private static final String URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String USUARIO = "postgres"
    private static final String SENHA = "2010"

    Connection conectar(){
        return DriverManager.getConnection(URL, USUARIO, SENHA)
    }
}
