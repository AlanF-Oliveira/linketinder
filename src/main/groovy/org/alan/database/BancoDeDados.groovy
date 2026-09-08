package org.alan.database

import org.alan.model.Candidato

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class BancoDeDados {

    private static final String URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String USUARIO = "postgres"
    private static final String SENHA = "2010"

    Connection conectar(){
        return DriverManager.getConnection(URL, USUARIO, SENHA)
    }

    //========================== Candidato ==========================

    int insertCandidato(Candidato candidato){
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
        candidato.competencias.each { nomeCompetencia ->
            int idCompetencia = buscarOuCriarCompetencia(nomeCompetencia)
            insertCandidatoCompetencia(idGerado, idCompetencia)
        }
        return idGerado
    }

    Integer buscarCompetencia(String competencia) {
        Connection connection = conectar()
        String sql = "SELECT * FROM competencias WHERE competencia = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, competencia)
        ResultSet rs = ps.executeQuery()
        if (rs.next()){
            connection.close()
            return rs.getInt("id")
        }else {
            connection.close()
            return null
        }
    }

    int criarCompetencia(String competencia){
        Connection connection = conectar()
        String sql = "INSERT INTO competencias (competencia) VALUES(?)"
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ps.setString(1, competencia)
        ps.executeUpdate()
        ResultSet rs = ps.getGeneratedKeys()
        int idGerado = 0
        if (rs.next()){
            idGerado = rs.getInt("id")
        }
        connection.close()
        return idGerado
    }

    int buscarOuCriarCompetencia(String competencia) {
       Integer busca = buscarCompetencia(competencia)
        if(busca == null){
           return criarCompetencia(competencia)
        }else {
            return busca
        }
    }

    void insertCandidatoCompetencia(int idCandidato, int idCompetencia){
        Connection connection = conectar()
        String sql = "INSERT INTO candidato_competencia (id_candidatos, id_competencias) VALUES (?,?)"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idCandidato)
        ps.setInt(2, idCompetencia)
        ps.executeUpdate()
        connection.close()
    }

    Candidato buscarCandidatoPorCpf(String cpf) {
        Connection connection = conectar()
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cpf)
        ResultSet rs = ps.executeQuery()

        Candidato candidato = null
        if (rs.next()) {
            candidato = new Candidato(
                    nome: rs.getString("nome"),
                    sobrenome: rs.getString("sobrenome"),
                    nascimento: rs.getDate("nascimento").toLocalDate(),
                    email: rs.getString("email"),
                    cpf: rs.getString("cpf"),
                    descricao: rs.getString("descricao"),
                    pais: rs.getString("pais"),
                    estado: rs.getString("estado"),
                    cidade: rs.getString("cidade"),
                    cep: rs.getString("cep"),
                    senha: rs.getString("senha")
            )
        }
        connection.close()
        return candidato
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

    boolean deletarCandidato(String cpfCandidato){

        Connection connection = conectar()
        String sql = "DELETE FROM candidatos WHERE cpf = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cpfCandidato)
        int linhasAtualizadas = ps.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    //========================== Empresa ==========================
}
