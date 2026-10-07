package org.alan.database

import java.sql.Connection
import java.sql.DriverManager

class ConnectionFactory {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/linketinder"

    private static final String USUARIO = "postgres"

    Connection criarConexao() {
        String senha = System.getenv("DB_SENHA")
        if (!senha) {
            throw new IllegalStateException(
                    "DB_SENHA não configurada"
            )
        }

        return DriverManager.getConnection(URL, USUARIO, senha)
    }
}