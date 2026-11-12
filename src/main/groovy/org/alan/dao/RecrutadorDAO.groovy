package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Empresa
import org.alan.model.Recrutador
import org.alan.repository.EmpresaRepository
import org.alan.repository.RecrutadorRepository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDate

class RecrutadorDAO implements RecrutadorRepository {

    private final ConnectionFactory connectionFactory
    private final EmpresaRepository empresaRepository

    RecrutadorDAO(ConnectionFactory connectionFactory, EmpresaRepository empresaRepository) {
        this.connectionFactory = connectionFactory
        this.empresaRepository = empresaRepository
    }

    private Recrutador criarRecrutador(ResultSet resultado) {
        Empresa empresa = empresaRepository.buscarEmpresaPorId(resultado.getInt("id_empresa"))
        return new Recrutador(
                id: resultado.getInt("id"),
                nome: resultado.getString("nome"),
                areaAtuacao: resultado.getString("area_atuacao"),
                descricao: resultado.getString("descricao"),
                email: resultado.getString("email"),
                cpf: resultado.getString("cpf"),
                nascimento: resultado.getDate("nascimento").toLocalDate(),
                senha: resultado.getString("senha"),
                empresa: empresa,
                status: resultado.getString("status")
        )
    }

    int inserir(Recrutador recrutador, int idEmpresa) {
        Connection connection = connectionFactory.criarConexao()
        String sql = "INSERT INTO recrutadores " +
                "(nome, area_atuacao, descricao, email, cpf, nascimento, senha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, recrutador.nome)
        statement.setString(2, recrutador.areaAtuacao)
        statement.setString(3, recrutador.descricao)
        statement.setString(4, recrutador.email)
        statement.setString(5, recrutador.cpf)
        statement.setObject(6, recrutador.nascimento)
        statement.setString(7, recrutador.senha)
        statement.executeUpdate()

        ResultSet keys = statement.getGeneratedKeys()
        int idRecrutador = keys.next() ? keys.getInt(1) : 0
        if (idRecrutador != 0) {
            PreparedStatement vinculo = connection.prepareStatement(
                    "INSERT INTO recrutador_empresa (id_recrutador, id_empresa, status) VALUES (?, ?, ?)"
            )
            vinculo.setInt(1, idRecrutador)
            vinculo.setInt(2, idEmpresa)
            vinculo.setString(3, recrutador.status ?: "ATIVO")
            vinculo.executeUpdate()
        }
        connection.close()
        return idRecrutador
    }

    Recrutador buscarPorCpf(String cpf) {
        executarBusca(
                "SELECT r.*, re.id_empresa, re.status FROM recrutadores r " +
                        "JOIN recrutador_empresa re ON re.id_recrutador = r.id WHERE r.cpf = ?",
                [cpf]
        )
    }

    Recrutador buscarPorCpfNaEmpresa(String cpf, String cnpjEmpresa) {
        executarBusca(
                "SELECT r.*, re.id_empresa, re.status FROM recrutadores r " +
                        "JOIN recrutador_empresa re ON re.id_recrutador = r.id " +
                        "JOIN empresa e ON e.id = re.id_empresa " +
                        "WHERE r.cpf = ? AND e.cnpj = ?",
                [cpf, cnpjEmpresa]
        )
    }

    boolean possuiVinculoAtivo(String cpf, String cnpjEmpresa) {
        Connection connection = connectionFactory.criarConexao()
        PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 FROM recrutador_empresa re " +
                        "JOIN recrutadores r ON r.id = re.id_recrutador " +
                        "JOIN empresa e ON e.id = re.id_empresa " +
                        "WHERE r.cpf = ? AND e.cnpj = ? AND re.status = 'ATIVO'"
        )
        statement.setString(1, cpf)
        statement.setString(2, cnpjEmpresa)
        ResultSet resultado = statement.executeQuery()
        boolean ativo = resultado.next()
        connection.close()
        return ativo
    }

    List<Recrutador> listarPorEmpresa(String cnpjEmpresa) {
        Connection connection = connectionFactory.criarConexao()
        PreparedStatement statement = connection.prepareStatement(
                "SELECT r.*, re.id_empresa, re.status FROM recrutadores r " +
                        "JOIN recrutador_empresa re ON re.id_recrutador = r.id " +
                        "JOIN empresa e ON e.id = re.id_empresa WHERE e.cnpj = ? ORDER BY r.id"
        )
        statement.setString(1, cnpjEmpresa)
        ResultSet resultado = statement.executeQuery()
        List<Recrutador> recrutadores = []
        while (resultado.next()) {
            recrutadores.add(criarRecrutador(resultado))
        }
        connection.close()
        return recrutadores
    }

    boolean atualizar(Recrutador recrutador, String cnpjEmpresa) {
        Connection connection = connectionFactory.criarConexao()
        String statusAnterior = null
        PreparedStatement consultaStatus = connection.prepareStatement(
                "SELECT re.status FROM recrutador_empresa re " +
                        "JOIN recrutadores r ON r.id = re.id_recrutador " +
                        "JOIN empresa e ON e.id = re.id_empresa " +
                        "WHERE r.cpf = ? AND e.cnpj = ?"
        )
        consultaStatus.setString(1, recrutador.cpf)
        consultaStatus.setString(2, cnpjEmpresa)
        ResultSet resultadoStatus = consultaStatus.executeQuery()
        if (resultadoStatus.next()) {
            statusAnterior = resultadoStatus.getString("status")
        }
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE recrutadores r SET nome = ?, area_atuacao = ?, descricao = ?, " +
                        "email = ?, nascimento = ?, senha = ? FROM recrutador_empresa re, empresa e " +
                        "WHERE r.id = re.id_recrutador AND re.id_empresa = e.id " +
                        "AND r.cpf = ? AND e.cnpj = ?"
        )
        statement.setString(1, recrutador.nome)
        statement.setString(2, recrutador.areaAtuacao)
        statement.setString(3, recrutador.descricao)
        statement.setString(4, recrutador.email)
        statement.setObject(5, recrutador.nascimento)
        statement.setString(6, recrutador.senha)
        statement.setString(7, recrutador.cpf)
        statement.setString(8, cnpjEmpresa)
        int linhas = statement.executeUpdate()

        if (linhas > 0 && recrutador.status) {
            PreparedStatement status = connection.prepareStatement(
                    "UPDATE recrutador_empresa re SET status = ? FROM recrutadores r " +
                            "JOIN empresa e ON e.id = re.id_empresa " +
                            "WHERE re.id_recrutador = r.id AND r.cpf = ? AND e.cnpj = ?"
            )
            status.setString(1, recrutador.status)
            status.setString(2, recrutador.cpf)
            status.setString(3, cnpjEmpresa)
            status.executeUpdate()
            if (statusAnterior != null && statusAnterior != recrutador.status) {
                PreparedStatement historico = connection.prepareStatement(
                        "INSERT INTO historico_vinculo_recrutador " +
                                "(id_recrutador, status_anterior, novo_status, responsavel) " +
                                "SELECT r.id, ?, ?, ? FROM recrutadores r WHERE r.cpf = ?"
                )
                historico.setString(1, statusAnterior)
                historico.setString(2, recrutador.status)
                historico.setString(3, cnpjEmpresa)
                historico.setString(4, recrutador.cpf)
                historico.executeUpdate()
            }
        }
        connection.close()
        return linhas > 0
    }

    boolean deletar(String cpf, String cnpjEmpresa) {
        Connection connection = connectionFactory.criarConexao()
        connection.autoCommit = false
        try {
            PreparedStatement snapshot = connection.prepareStatement(
                    "INSERT INTO recrutador_responsavel_historico " +
                            "(id_recrutador_origem, nome, area_atuacao, removido) " +
                            "SELECT r.id, r.nome, r.area_atuacao, TRUE FROM recrutadores r " +
                            "JOIN recrutador_empresa re ON re.id_recrutador = r.id " +
                            "JOIN empresa e ON e.id = re.id_empresa " +
                            "WHERE r.cpf = ? AND e.cnpj = ?"
            )
            snapshot.setString(1, cpf)
            snapshot.setString(2, cnpjEmpresa)
            snapshot.executeUpdate()

            PreparedStatement delete = connection.prepareStatement(
                    "DELETE FROM recrutadores r USING recrutador_empresa re, empresa e " +
                            "WHERE r.id = re.id_recrutador AND re.id_empresa = e.id " +
                            "AND r.cpf = ? AND e.cnpj = ?"
            )
            delete.setString(1, cpf)
            delete.setString(2, cnpjEmpresa)
            int linhas = delete.executeUpdate()
            connection.commit()
            connection.close()
            return linhas > 0
        } catch (Exception erro) {
            connection.rollback()
            connection.close()
            throw erro
        }
    }

    private Recrutador executarBusca(String sql, List<String> parametros) {
        Connection connection = connectionFactory.criarConexao()
        PreparedStatement statement = connection.prepareStatement(sql)
        parametros.eachWithIndex { String parametro, int indice ->
            statement.setString(indice + 1, parametro)
        }
        ResultSet resultado = statement.executeQuery()
        Recrutador recrutador = resultado.next() ? criarRecrutador(resultado) : null
        connection.close()
        return recrutador
    }
}
