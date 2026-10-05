package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class VagaDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory()
    private final CompetenciaDAO competenciaDAO = new CompetenciaDAO()
    private final EmpresaDAO empresaDAO = new EmpresaDAO()

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    void insertVagasCompetencia(int idVaga, int idCompetencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO vagas_competencias (id_vagas, id_competencias) VALUES (?,?)"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idVaga)
        ps.setInt(2, idCompetencia)
        ps.executeUpdate()
        connection.close()
    }

    int insertVaga(Vaga vaga, int idEmpresa) {
        Connection connection = conectar()
        String sql = "INSERT INTO vagas (titulo, descricao, estado, cidade, id_empresa) VALUES(?, ?, ? ,?, ?)"
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ps.setString(1, vaga.titulo)
        ps.setString(2, vaga.descricao)
        ps.setString(3, vaga.estado)
        ps.setString(4, vaga.cidade)
        ps.setInt(5, idEmpresa)
        ps.executeUpdate()
        ResultSet rs = ps.getGeneratedKeys()
        int idGerado = 0;
        if (rs.next()) {
            idGerado = rs.getInt("id")
            vaga.id = idGerado
        }
        connection.close()
        vaga.competenciasExigidas.each { nomeCompetencia ->
            int idCompetencia = competenciaDAO.buscarOuCriarCompetencia(nomeCompetencia)
            insertVagasCompetencia(idGerado, idCompetencia)
        }
        return idGerado
    }

    List<String> buscarCompetenciasDaVaga(int idVaga) {
        Connection connection = conectar()
        String sql = "SELECT c.competencia FROM vagas_competencias vc JOIN competencias c ON vc.id_competencias = c.id WHERE vc.id_vagas = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idVaga)
        ResultSet rs = ps.executeQuery()
        List<String> competencias = []
        while (rs.next()) {
            String competencia = rs.getString("competencia")
            competencias.add(competencia)
        }
        connection.close()
        return competencias
    }

    Vaga buscarVagaPorId(int idVaga) {
        Connection connection = conectar()
        String sql = "SELECT * FROM vagas WHERE id = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idVaga)
        ResultSet rs = ps.executeQuery()
        Vaga vaga = null
        if (rs.next()) {
            vaga = new Vaga(
                    id: rs.getInt("id"),
                    titulo: rs.getString("titulo"),
                    descricao: rs.getString("descricao"),
                    estado: rs.getString("estado"),
                    cidade: rs.getString("cidade"),
                    competenciasExigidas: buscarCompetenciasDaVaga(rs.getInt("id")),
                    empresa: empresaDAO.buscarEmpresaPorId(rs.getInt("id_empresa"))
            )
        }
        connection.close()
        return vaga
    }

    List<Vaga> listarVagas() {
        Connection connection = conectar()
        String sql = "SELECT * FROM vagas"
        PreparedStatement ps = connection.prepareStatement(sql)
        ResultSet rs = ps.executeQuery()
        List<Vaga> vagas = []
        while (rs.next()) {
            Vaga vaga = new Vaga(
                    id: rs.getInt("id"),
                    titulo: rs.getString("titulo"),
                    descricao: rs.getString("descricao"),
                    estado: rs.getString("estado"),
                    cidade: rs.getString("cidade"),
                    competenciasExigidas: buscarCompetenciasDaVaga(rs.getInt("id")),
                    empresa: empresaDAO.buscarEmpresaPorId(rs.getInt("id_empresa"))
            )
            vagas.add(vaga)
        }
        connection.close()
        return vagas
    }

    boolean atualizarVaga(Vaga vaga) {
        Connection connection = conectar()
        String sql = "UPDATE vagas SET titulo = ?, descricao = ?, estado = ?,  cidade = ? WHERE id = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, vaga.titulo)
        ps.setString(2, vaga.descricao)
        ps.setString(3, vaga.estado)
        ps.setString(4, vaga.cidade)
        ps.setInt(5, vaga.id)
        int linhasAtualizadas = ps.executeUpdate()
        connection.close()
        if (linhasAtualizadas > 0) {
            Connection connectionCompetencias = conectar()
            String sqlDel = "DELETE FROM vagas_competencias WHERE id_vagas = ?"
            PreparedStatement psDelete = connectionCompetencias.prepareStatement(sqlDel)
            psDelete.setInt(1, vaga.id)
            psDelete.executeUpdate()

            connectionCompetencias.close()
            vaga.competenciasExigidas.each { nomeCompetencia ->
                int idCompetencia = competenciaDAO.buscarOuCriarCompetencia(nomeCompetencia)
                insertVagasCompetencia(vaga.id, idCompetencia)
            }
            return true
        }
        return false
    }

    boolean deletarVaga(int idVaga) {
        Connection connection = conectar()
        String sqlDelComp = "DELETE FROM vagas_competencias WHERE id_vagas = ?"
        PreparedStatement psCompetencias = connection.prepareStatement(sqlDelComp)
        psCompetencias.setInt(1, idVaga)
        psCompetencias.executeUpdate()
        String sqlDelVaga = "DELETE FROM vagas WHERE id = ?"
        PreparedStatement psVaga = connection.prepareStatement(sqlDelVaga)
        psVaga.setInt(1, idVaga)
        int linhasAtualizadas = psVaga.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }
}

