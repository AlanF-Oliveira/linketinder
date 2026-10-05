package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Candidato

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class CandidatoDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory()
    private final CompetenciaDAO competenciaDAO = new CompetenciaDAO()

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    private Candidato criarCandidato(ResultSet resultado) {
        int idCandidato = resultado.getInt("id")

        return new Candidato(
                id: idCandidato,
                nome: resultado.getString("nome"),
                sobrenome: resultado.getString("sobrenome"),
                nascimento: resultado.getDate("nascimento").toLocalDate(),
                email: resultado.getString("email"),
                cpf: resultado.getString("cpf"),
                descricao: resultado.getString("descricao"),
                pais: resultado.getString("pais"),
                estado: resultado.getString("estado"),
                cidade: resultado.getString("cidade"),
                cep: resultado.getString("cep"),
                senha: resultado.getString("senha"),
                competencias: buscarCompetenciasDoCandidato(idCandidato)
        )
    }

    int inserir(Candidato candidato) {
        Connection connection = conectar()
        String sql = "INSERT INTO candidatos (nome, sobrenome, nascimento, email, cpf, descricao, pais, estado, cidade, cep, senha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ps.setString(1, candidato.getNome())
        ps.setString(2, candidato.getSobrenome())
        ps.setDate(3, java.sql.Date.valueOf(candidato.getNascimento()))
        ps.setString(4, candidato.getEmail())
        ps.setString(5, candidato.getCpf())
        ps.setString(6, candidato.getDescricao())
        ps.setString(7, candidato.getPais())
        ps.setString(8, candidato.getEstado())
        ps.setString(9, candidato.getCidade())
        ps.setString(10, candidato.getCep())
        ps.setString(11, candidato.getSenha())
        ps.executeUpdate()
        ResultSet rs = ps.getGeneratedKeys()
        int idGerado = 0
        if (rs.next()) {
            idGerado = rs.getInt(1)
        }

        connection.close()

        if (idGerado == 0) {
            return 0
        }

        candidato.competencias.each { nomeCompetencia ->
            int idCompetencia = competenciaDAO.buscarOuCriarCompetencia(nomeCompetencia)
            insertCandidatoCompetencia(idGerado, idCompetencia)
        }
        return idGerado
    }


    private void insertCandidatoCompetencia(int idCandidato, int idCompetencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO candidato_competencia (id_candidatos, id_competencias) VALUES (?,?)"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idCandidato)
        ps.setInt(2, idCompetencia)
        ps.executeUpdate()
        connection.close()
    }

    private List<String> buscarCompetenciasDoCandidato(int idCandidato) {
        Connection connection = conectar()
        String sql = "SELECT c.competencia FROM candidato_competencia cc JOIN competencias c ON cc.id_competencias = c.id WHERE cc.id_candidatos = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idCandidato)
        ResultSet rs = ps.executeQuery()
        List<String> competencias = []
        while (rs.next()) {
            String competencia = rs.getString("competencia")
            competencias.add(competencia)
        }
        connection.close()
        return competencias
    }

    Candidato buscarCandidatoPorCpf(String cpf) {
        Connection connection = conectar()
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cpf)
        ResultSet rs = ps.executeQuery()

        Candidato candidato = null
        if (rs.next()) {
            candidato = criarCandidato(rs)
        }
        connection.close()
        return candidato
    }

    List<Candidato> listarCandidatos() {
        Connection connection = conectar()
        String sql = "SELECT * FROM candidatos"
        PreparedStatement ps = connection.prepareStatement(sql)
        ResultSet rs = ps.executeQuery()
        List<Candidato> candidatos = [];
        while (rs.next()) {
            candidatos.add(criarCandidato(rs))
        }
        connection.close()
        return candidatos
    }

    boolean atualizarCandidato(Candidato candidato) {

        Connection connection = conectar()
        String sql = "UPDATE candidatos SET nome = ?, sobrenome = ?, nascimento = ?, email = ?, descricao = ?, pais = ?, estado = ?, cidade = ?, cep = ?, senha = ? WHERE cpf = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, candidato.getNome())
        ps.setString(2, candidato.getSobrenome())
        ps.setDate(3, java.sql.Date.valueOf(candidato.getNascimento()))
        ps.setString(4, candidato.getEmail())
        ps.setString(5, candidato.getDescricao())
        ps.setString(6, candidato.getPais())
        ps.setString(7, candidato.getEstado())
        ps.setString(8, candidato.getCidade())
        ps.setString(9, candidato.getCep())
        ps.setString(10, candidato.getSenha())
        ps.setString(11, candidato.getCpf())

        int linhasAtualizadas = ps.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarCandidato(String cpfCandidato) {

        Connection connection = conectar()
        String sql = "SELECT id FROM candidatos WHERE cpf = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cpfCandidato)
        ResultSet rs = ps.executeQuery()
        if (!rs.next()) {
            connection.close()
            return false
        }
        int idCandidato = rs.getInt("id")
        String sqlDelComp = "DELETE FROM candidato_competencia WHERE id_candidatos = ?"
        PreparedStatement psComp = connection.prepareStatement(sqlDelComp)
        psComp.setInt(1, idCandidato)
        psComp.executeUpdate()
        String sqlDelCandidato = "DELETE FROM candidatos WHERE cpf = ?"
        PreparedStatement psCandidato = connection.prepareStatement(sqlDelCandidato)
        psCandidato.setString(1, cpfCandidato)
        int linhasAtualizadas = psCandidato.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

}
