package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Match
import org.alan.repository.MatchRepository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class MatchDAO implements MatchRepository {

    private final ConnectionFactory connectionFactory

    MatchDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory
    }

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    List<Match> curtirVaga(int idCandidato, int idVaga) {
        Connection connection = conectar()
        try {
            connection.autoCommit = false
            executar(connection,
                    "INSERT INTO curtidas_candidato_vaga (id_candidato, id_vaga) " +
                            "VALUES (?, ?) ON CONFLICT DO NOTHING",
                    idCandidato, idVaga)
            List<Match> matches = criarMatchesDaVaga(connection, idCandidato, idVaga)
            connection.commit()
            return matches
        } catch (Exception erro) {
            connection.rollback()
            throw erro
        } finally {
            connection.close()
        }
    }

    List<Match> curtirCandidato(int idEmpresa, int idCandidato) {
        Connection connection = conectar()
        try {
            connection.autoCommit = false
            executar(connection,
                    "INSERT INTO curtidas_empresa_candidato (id_empresa, id_candidato) " +
                            "VALUES (?, ?) ON CONFLICT DO NOTHING",
                    idEmpresa, idCandidato)
            List<Match> matches = criarMatchesDoCandidato(connection, idEmpresa, idCandidato)
            connection.commit()
            return matches
        } catch (Exception erro) {
            connection.rollback()
            throw erro
        } finally {
            connection.close()
        }
    }

    List<Match> curtirCandidatoComoRecrutador(
            int idEmpresa, int idCandidato, int idRecrutador
    ) {
        Connection connection = conectar()
        try {
            connection.autoCommit = false
            executar(connection,
                    "INSERT INTO curtidas_empresa_candidato (id_empresa, id_candidato) " +
                            "VALUES (?, ?) ON CONFLICT DO NOTHING",
                    idEmpresa, idCandidato)
            int idResponsavel = inserirResponsavelHistorico(
                    connection, idEmpresa, idRecrutador
            )
            executar(connection,
                    "INSERT INTO curtida_empresa_responsavel " +
                            "(id_empresa, id_candidato, id_responsavel_historico) " +
                            "VALUES (?, ?, ?) ON CONFLICT DO NOTHING",
                    idEmpresa, idCandidato, idResponsavel)
            List<Match> matches = criarMatchesDoCandidato(
                    connection, idEmpresa, idCandidato
            )
            connection.commit()
            return matches
        } catch (Exception erro) {
            connection.rollback()
            throw erro
        } finally {
            connection.close()
        }
    }

    private int inserirResponsavelHistorico(
            Connection connection, int idEmpresa, int idRecrutador
    ) {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO recrutador_responsavel_historico " +
                        "(id_recrutador_origem, nome, area_atuacao, removido) " +
                        "SELECT r.id, r.nome, r.area_atuacao, FALSE " +
                        "FROM recrutadores r " +
                        "JOIN recrutador_empresa re ON re.id_recrutador = r.id " +
                        "WHERE r.id = ? AND re.id_empresa = ? AND re.status = 'ATIVO' " +
                        "RETURNING id"
        )
        statement.setInt(1, idRecrutador)
        statement.setInt(2, idEmpresa)
        ResultSet resultado = statement.executeQuery()
        if (!resultado.next()) {
            throw new Exception("Recrutador sem vínculo ativo com esta empresa")
        }
        return resultado.getInt("id")
    }

    private List<Match> criarMatchesDaVaga(
            Connection connection, int idCandidato, int idVaga
    ) {
        String sql = "SELECT v.id AS id_vaga, v.titulo AS nome_vaga, " +
                "v.descricao AS descricao_vaga, v.cidade || ', ' || v.estado AS local_vaga, " +
                "c.id AS id_candidato, c.nome || ' ' || c.sobrenome AS nome_candidato, " +
                "c.email AS email_candidato, c.descricao AS descricao_candidato, " +
                "c.cidade || ', ' || c.estado AS local_candidato, " +
                "e.id AS id_empresa, e.nome AS nome_empresa, e.email AS email_empresa, " +
                "e.descricao AS descricao_empresa, e.cidade || ', ' || e.estado AS local_empresa " +
                "FROM vagas v " +
                "JOIN empresa e ON e.id = v.id_empresa " +
                "JOIN candidatos c ON c.id = ? " +
                "JOIN curtidas_empresa_candidato ce ON ce.id_empresa = e.id " +
                "AND ce.id_candidato = c.id WHERE v.id = ?"
        return criarMatches(connection, sql, idCandidato, idVaga)
    }

    private List<Match> criarMatchesDoCandidato(
            Connection connection, int idEmpresa, int idCandidato
    ) {
        String sql = "SELECT v.id AS id_vaga, v.titulo AS nome_vaga, " +
                "v.descricao AS descricao_vaga, v.cidade || ', ' || v.estado AS local_vaga, " +
                "c.id AS id_candidato, c.nome || ' ' || c.sobrenome AS nome_candidato, " +
                "c.email AS email_candidato, c.descricao AS descricao_candidato, " +
                "c.cidade || ', ' || c.estado AS local_candidato, " +
                "e.id AS id_empresa, e.nome AS nome_empresa, e.email AS email_empresa, " +
                "e.descricao AS descricao_empresa, e.cidade || ', ' || e.estado AS local_empresa " +
                "FROM curtidas_candidato_vaga cc " +
                "JOIN vagas v ON v.id = cc.id_vaga " +
                "JOIN empresa e ON e.id = v.id_empresa AND e.id = ? " +
                "JOIN candidatos c ON c.id = cc.id_candidato AND c.id = ?"
        return criarMatches(connection, sql, idEmpresa, idCandidato)
    }

    private List<Match> criarMatches(
            Connection connection, String sql, int primeiro, int segundo
    ) {
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setInt(1, primeiro)
        statement.setInt(2, segundo)
        ResultSet resultado = statement.executeQuery()
        List<Match> matches = []
        while (resultado.next()) {
            inserirMatch(connection, resultado)
            matches.add(criarMatchSemId(resultado))
        }
        return matches
    }

    private void inserirMatch(Connection connection, ResultSet resultado) {
        executar(connection,
                "INSERT INTO matches (id_vaga, id_candidato, id_empresa) " +
                        "VALUES (?, ?, ?) ON CONFLICT (id_vaga, id_candidato) DO NOTHING",
                resultado.getInt("id_vaga"), resultado.getInt("id_candidato"),
                resultado.getInt("id_empresa"))
    }

    List<Match> listarPorCandidato(int idCandidato) {
        return listar("WHERE m.id_candidato = ?", idCandidato)
    }

    List<Match> listarPorEmpresa(int idEmpresa) {
        return listar("WHERE m.id_empresa = ?", idEmpresa)
    }

    private List<Match> listar(String filtro, int id) {
        Connection connection = conectar()
        try {
            String sql = "SELECT m.id, m.id_vaga, m.id_candidato, m.id_empresa, " +
                    "v.titulo AS nome_vaga, v.descricao AS descricao_vaga, " +
                    "v.cidade || ', ' || v.estado AS local_vaga, " +
                    "c.nome || ' ' || c.sobrenome AS nome_candidato, " +
                    "c.email AS email_candidato, c.descricao AS descricao_candidato, " +
                    "c.cidade || ', ' || c.estado AS local_candidato, " +
                    "e.nome AS nome_empresa, e.email AS email_empresa, " +
                    "e.descricao AS descricao_empresa, " +
                    "e.cidade || ', ' || e.estado AS local_empresa FROM matches m " +
                    "JOIN vagas v ON v.id = m.id_vaga " +
                    "JOIN candidatos c ON c.id = m.id_candidato " +
                    "JOIN empresa e ON e.id = m.id_empresa " + filtro
            PreparedStatement statement = connection.prepareStatement(sql)
            statement.setInt(1, id)
            ResultSet resultado = statement.executeQuery()
            List<Match> matches = []
            while (resultado.next()) {
                matches.add(criarMatch(resultado))
            }
            return matches
        } finally {
            connection.close()
        }
    }

    private Match criarMatch(ResultSet resultado) {
        return new Match(
                id: resultado.getInt("id"),
                idVaga: resultado.getInt("id_vaga"),
                idCandidato: resultado.getInt("id_candidato"),
                idEmpresa: resultado.getInt("id_empresa"),
                nomeDaVaga: resultado.getString("nome_vaga"),
                descricaoDaVaga: resultado.getString("descricao_vaga"),
                localDaVaga: resultado.getString("local_vaga"),
                nomeCandidato: resultado.getString("nome_candidato"),
                emailCandidato: resultado.getString("email_candidato"),
                descricaoDoCandidato: resultado.getString("descricao_candidato"),
                localDoCandidato: resultado.getString("local_candidato"),
                nomeEmpresa: resultado.getString("nome_empresa"),
                emailEmpresa: resultado.getString("email_empresa"),
                descricaoDaEmpresa: resultado.getString("descricao_empresa"),
                localDaEmpresa: resultado.getString("local_empresa")
        )
    }

    private Match criarMatchSemId(ResultSet resultado) {
        return new Match(
                idVaga: resultado.getInt("id_vaga"),
                idCandidato: resultado.getInt("id_candidato"),
                idEmpresa: resultado.getInt("id_empresa"),
                nomeDaVaga: resultado.getString("nome_vaga"),
                descricaoDaVaga: resultado.getString("descricao_vaga"),
                localDaVaga: resultado.getString("local_vaga"),
                nomeCandidato: resultado.getString("nome_candidato"),
                emailCandidato: resultado.getString("email_candidato"),
                descricaoDoCandidato: resultado.getString("descricao_candidato"),
                localDoCandidato: resultado.getString("local_candidato"),
                nomeEmpresa: resultado.getString("nome_empresa"),
                emailEmpresa: resultado.getString("email_empresa"),
                descricaoDaEmpresa: resultado.getString("descricao_empresa"),
                localDaEmpresa: resultado.getString("local_empresa")
        )
    }

    private static void executar(Connection connection, String sql, int... valores) {
        PreparedStatement statement = connection.prepareStatement(sql)
        valores.eachWithIndex { int valor, int indice ->
            statement.setInt(indice + 1, valor)
        }
        statement.executeUpdate()
    }
}
