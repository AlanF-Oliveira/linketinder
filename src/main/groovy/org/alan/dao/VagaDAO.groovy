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

    private Vaga criarVaga(ResultSet resultado) {
        int idVaga = resultado.getInt("id")

        return new Vaga(
                id: idVaga,
                titulo: resultado.getString("titulo"),
                descricao: resultado.getString("descricao"),
                estado: resultado.getString("estado"),
                cidade: resultado.getString("cidade"),
                competenciasExigidas: buscarCompetenciasDaVaga(idVaga),
                empresa: empresaDAO.buscarEmpresaPorId(
                        resultado.getInt("id_empresa")
                )
        )
    }

    int inserir(Vaga vaga, int idEmpresa) {
        Connection connection = conectar()
        String sql = "INSERT INTO vagas " +
                "(titulo, descricao, estado, cidade, id_empresa) " +
                "VALUES (?, ?, ?, ?, ?)"
        PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )

        statement.setString(1, vaga.titulo)
        statement.setString(2, vaga.descricao)
        statement.setString(3, vaga.estado)
        statement.setString(4, vaga.cidade)
        statement.setInt(5, idEmpresa)
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

        vaga.competenciasExigidas.each { nomeCompetencia ->
            int idCompetencia =
                    competenciaDAO.buscarOuCriarCompetencia(nomeCompetencia)

            associarCompetenciaAVaga(idGerado, idCompetencia)
        }

        return idGerado
    }

    private void associarCompetenciaAVaga(
            int idVaga,
            int idCompetencia
    ) {
        Connection connection = conectar()
        String sql = "INSERT INTO vagas_competencias " +
                "(id_vagas, id_competencias) VALUES (?, ?)"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idVaga)
        statement.setInt(2, idCompetencia)
        statement.executeUpdate()

        connection.close()
    }

    private List<String> buscarCompetenciasDaVaga(int idVaga) {
        Connection connection = conectar()
        String sql = "SELECT c.competencia " +
                "FROM vagas_competencias vc " +
                "JOIN competencias c ON vc.id_competencias = c.id " +
                "WHERE vc.id_vagas = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idVaga)
        ResultSet resultado = statement.executeQuery()

        List<String> competencias = []

        while (resultado.next()) {
            competencias.add(resultado.getString("competencia"))
        }

        connection.close()
        return competencias
    }

    Vaga buscarVagaPorId(int idVaga) {
        Connection connection = conectar()
        String sql = "SELECT * FROM vagas WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idVaga)
        ResultSet resultado = statement.executeQuery()

        Vaga vaga = null

        if (resultado.next()) {
            vaga = criarVaga(resultado)
        }

        connection.close()
        return vaga
    }

    List<Vaga> listarVagas() {
        Connection connection = conectar()
        String sql = "SELECT * FROM vagas"
        PreparedStatement statement = connection.prepareStatement(sql)
        ResultSet resultado = statement.executeQuery()

        List<Vaga> vagas = []

        while (resultado.next()) {
            vagas.add(criarVaga(resultado))
        }

        connection.close()
        return vagas
    }

    boolean atualizarVaga(Vaga vaga) {
        Connection connection = conectar()
        String sql = "UPDATE vagas SET titulo = ?, descricao = ?, " +
                "estado = ?, cidade = ? WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setString(1, vaga.titulo)
        statement.setString(2, vaga.descricao)
        statement.setString(3, vaga.estado)
        statement.setString(4, vaga.cidade)
        statement.setInt(5, vaga.id)

        int linhasAtualizadas = statement.executeUpdate()
        connection.close()

        if (linhasAtualizadas == 0) {
            return false
        }

        atualizarCompetenciasDaVaga(vaga)
        return true
    }

    private void atualizarCompetenciasDaVaga(Vaga vaga) {
        removerCompetenciasDaVaga(vaga.id)

        vaga.competenciasExigidas.each { nomeCompetencia ->
            int idCompetencia =
                    competenciaDAO.buscarOuCriarCompetencia(nomeCompetencia)

            associarCompetenciaAVaga(vaga.id, idCompetencia)
        }
    }

    private void removerCompetenciasDaVaga(int idVaga) {
        Connection connection = conectar()
        String sql = "DELETE FROM vagas_competencias WHERE id_vagas = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idVaga)
        statement.executeUpdate()

        connection.close()
    }

    boolean deletarVaga(int idVaga) {
        Connection connection = conectar()
        String sql = "DELETE FROM vagas WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)

        statement.setInt(1, idVaga)
        int linhasAfetadas = statement.executeUpdate()

        connection.close()
        return linhasAfetadas > 0
    }
}