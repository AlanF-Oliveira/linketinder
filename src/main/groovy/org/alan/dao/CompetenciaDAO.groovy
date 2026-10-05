package org.alan.dao

import org.alan.database.ConnectionFactory

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class CompetenciaDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory()

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    Integer buscarCompetencia(String competencia) {
        Connection connection = conectar()
        String sql = "SELECT * FROM competencias WHERE competencia = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, competencia.toUpperCase())
        ResultSet rs = ps.executeQuery()
        if (rs.next()) {
            connection.close()
            return rs.getInt("id")
        } else {
            connection.close()
            return null
        }
    }

    int criarCompetencia(String competencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO competencias (competencia) VALUES(?)"
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ps.setString(1, competencia.toUpperCase())
        ps.executeUpdate()
        ResultSet rs = ps.getGeneratedKeys()
        int idGerado = 0
        if (rs.next()) {
            idGerado = rs.getInt("id")
        }
        connection.close()
        return idGerado
    }

    int buscarOuCriarCompetencia(String competencia) {
        Integer busca = buscarCompetencia(competencia)
        if (busca == null) {
            return criarCompetencia(competencia)
        } else {
            return busca
        }
    }

    List<String> listarCompetencias() {
        Connection connection = conectar()
        String sql = "SELECT id, competencia FROM competencias"
        PreparedStatement ps = connection.prepareStatement(sql)
        ResultSet rs = ps.executeQuery()
        List<String> competencias = []
        while (rs.next()) {
            int id = rs.getInt("id")
            String nome = rs.getString("competencia")
            competencias.add("$id - $nome")
        }
        connection.close()
        return competencias
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        Connection connection = conectar()
        String sql = "UPDATE competencias SET competencia = ? WHERE id = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, competencia.toUpperCase())
        ps.setInt(2, idCompetencia)
        int linhasAtualizadas = ps.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarCompetencia(int idCompetencia) {

        Connection connection = conectar()
        String sqlDelCandidatoComp = "DELETE FROM candidato_competencia WHERE id_competencias = ?"
        PreparedStatement psCandidatoComp = connection.prepareStatement(sqlDelCandidatoComp)
        psCandidatoComp.setInt(1, idCompetencia)
        psCandidatoComp.executeUpdate()
        String sqlDelVagaComp = "DELETE FROM vagas_competencias WHERE id_competencias = ?"
        PreparedStatement psVagaComp = connection.prepareStatement(sqlDelVagaComp)
        psVagaComp.setInt(1, idCompetencia)
        psVagaComp.executeUpdate()
        String sqlDelCompetencia = "DELETE FROM competencias WHERE id = ?"
        PreparedStatement psCompetencia = connection.prepareStatement(sqlDelCompetencia)
        psCompetencia.setInt(1, idCompetencia)
        int linhasAtualizadas = psCompetencia.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

}
