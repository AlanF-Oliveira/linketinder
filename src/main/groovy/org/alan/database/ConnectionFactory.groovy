package org.alan.database

import java.sql.Connection

interface ConnectionFactory {
    Connection criarConexao()
}