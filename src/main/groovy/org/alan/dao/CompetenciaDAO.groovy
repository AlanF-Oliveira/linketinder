package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.repository.CompetenciaRepository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class CompetenciaDAO implements CompetenciaRepository {

    private final ConnectionFactory connectionFactory

    CompetenciaDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory
    }

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    private Integer buscarIdPorNome(String competencia) {
        Connection connection = conectar()
        String sql = "SELECT id FROM competencias WHERE competencia = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setString(1, competencia.toUpperCase())
        ResultSet resultado = statement.executeQuery()

        Integer idCompetencia = null

        if (resultado.next()) {
            idCompetencia = resultado.getInt("id")
        }

        connection.close()
        return idCompetencia
    }

    private int inserir(String competencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO competencias (competencia) VALUES (?)"
        PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )

        statement.setString(1, competencia.toUpperCase())
        statement.executeUpdate()

        ResultSet resultado = statement.getGeneratedKeys()
        int idGerado = 0

        if (resultado.next()) {
            idGerado = resultado.getInt(1)
        }

        connection.close()
        return idGerado
    }

    int buscarOuCriarCompetencia(String competencia) {
        Integer idCompetencia = buscarIdPorNome(competencia)

        if (idCompetencia != null) {
            return idCompetencia
        }

        return inserir(competencia)
    }

    List<String> listarCompetencias() {
        Connection connection = conectar()
        String sql = "SELECT id, competencia FROM competencias"
        PreparedStatement statement = connection.prepareStatement(sql)
        ResultSet resultado = statement.executeQuery()

        List<String> competencias = []

        while (resultado.next()) {
            int id = resultado.getInt("id")
            String nome = resultado.getString("competencia")
            competencias.add("$id - $nome")
        }

        connection.close()
        return competencias
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        Connection connection = conectar()
        String sql = "UPDATE competencias " +
                "SET competencia = ? WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setString(1, competencia.toUpperCase())
        statement.setInt(2, idCompetencia)

        int linhasAtualizadas = statement.executeUpdate()

        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarCompetencia(int idCompetencia) {
        Connection connection = conectar()
        String sql = "DELETE FROM competencias WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idCompetencia)
        int linhasAfetadas = statement.executeUpdate()

        connection.close()
        return linhasAfetadas > 0
    }
}