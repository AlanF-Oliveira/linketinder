package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Candidato
import org.alan.repository.CandidatoRepository
import org.alan.repository.CompetenciaRepository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class CandidatoDAO implements CandidatoRepository {

    private final ConnectionFactory connectionFactory = new ConnectionFactory()
    private final CompetenciaRepository competenciaRepository

    CandidatoDAO(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository
    }

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
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, candidato.getNome())
        statement.setString(2, candidato.getSobrenome())
        statement.setDate(3, java.sql.Date.valueOf(candidato.getNascimento()))
        statement.setString(4, candidato.getEmail())
        statement.setString(5, candidato.getCpf())
        statement.setString(6, candidato.getDescricao())
        statement.setString(7, candidato.getPais())
        statement.setString(8, candidato.getEstado())
        statement.setString(9, candidato.getCidade())
        statement.setString(10, candidato.getCep())
        statement.setString(11, candidato.getSenha())
        statement.executeUpdate()
        ResultSet resultado = statement.getGeneratedKeys()
        int idGerado = 0
        if (resultado.next()) {
            idGerado = resultado.getInt(1)
        }

        connection.close()

        if (idGerado == 0) {
            return 0
        }

        candidato.competencias.each { nomeCompetencia ->
            int idCompetencia = competenciaRepository.buscarOuCriarCompetencia(nomeCompetencia)
            associarCompetenciaAoCandidato(idGerado, idCompetencia)
        }
        return idGerado
    }


    private void associarCompetenciaAoCandidato(int idCandidato, int idCompetencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO candidato_competencia (id_candidatos, id_competencias) VALUES (?,?)"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setInt(1, idCandidato)
        statement.setInt(2, idCompetencia)
        statement.executeUpdate()
        connection.close()
    }

    private List<String> buscarCompetenciasDoCandidato(int idCandidato) {
        Connection connection = conectar()
        String sql = "SELECT c.competencia FROM candidato_competencia cc " +
                "JOIN competencias c ON cc.id_competencias = c.id WHERE cc.id_candidatos = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setInt(1, idCandidato)
        ResultSet resultado = statement.executeQuery()
        List<String> competencias = []
        while (resultado.next()) {
            String competencia = resultado.getString("competencia")
            competencias.add(competencia)
        }
        connection.close()
        return competencias
    }

    Candidato buscarCandidatoPorCpf(String cpf) {
        Connection connection = conectar()
        String sql = "SELECT * FROM candidatos WHERE cpf = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, cpf)
        ResultSet resultado = statement.executeQuery()

        Candidato candidato = null
        if (resultado.next()) {
            candidato = criarCandidato(resultado)
        }
        connection.close()
        return candidato
    }

    List<Candidato> listarCandidatos() {
        Connection connection = conectar()
        String sql = "SELECT * FROM candidatos"
        PreparedStatement statement = connection.prepareStatement(sql)
        ResultSet resultado = statement.executeQuery()
        List<Candidato> candidatos = [];
        while (resultado.next()) {
            candidatos.add(criarCandidato(resultado))
        }
        connection.close()
        return candidatos
    }

    boolean atualizarCandidato(Candidato candidato) {
        Connection connection = conectar()
        String sql = "UPDATE candidatos SET nome = ?, sobrenome = ?, nascimento = ?, email = ?, descricao = ?, " +
                "pais = ?, estado = ?, cidade = ?, cep = ?, senha = ? WHERE cpf = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, candidato.getNome())
        statement.setString(2, candidato.getSobrenome())
        statement.setDate(3, java.sql.Date.valueOf(candidato.getNascimento()))
        statement.setString(4, candidato.getEmail())
        statement.setString(5, candidato.getDescricao())
        statement.setString(6, candidato.getPais())
        statement.setString(7, candidato.getEstado())
        statement.setString(8, candidato.getCidade())
        statement.setString(9, candidato.getCep())
        statement.setString(10, candidato.getSenha())
        statement.setString(11, candidato.getCpf())

        int linhasAtualizadas = statement.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarCandidato(String cpfCandidato) {
        Connection connection = conectar()
        String sql = "DELETE FROM candidatos WHERE cpf = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, cpfCandidato)
        int linhasAfetadas = statement.executeUpdate()
        statement.close()
        connection.close()
        return linhasAfetadas > 0
    }

}
