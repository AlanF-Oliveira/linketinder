package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Empresa
import org.alan.repository.EmpresaRepository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class EmpresaDAO implements EmpresaRepository {

    private final ConnectionFactory connectionFactory

    EmpresaDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory
    }

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }

    private Empresa criarEmpresa(ResultSet resultado) {
        return new Empresa(
                id: resultado.getInt("id"),
                nome: resultado.getString("nome"),
                email: resultado.getString("email"),
                cnpj: resultado.getString("cnpj"),
                cidade: resultado.getString("cidade"),
                estado: resultado.getString("estado"),
                pais: resultado.getString("pais"),
                cep: resultado.getString("cep"),
                descricao: resultado.getString("descricao"),
                senha: resultado.getString("senha")
        )
    }

    int inserir(Empresa empresa) {
        Connection connection = conectar()
        String sql = "INSERT INTO empresa (nome, email, cnpj, descricao, pais, estado, cidade, cep, senha)" +
                " VALUES(?, ? ,? ,? ,? ,?, ? ,? ,?)"
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, empresa.nome)
        statement.setString(2, empresa.email)
        statement.setString(3, empresa.cnpj)
        statement.setString(4, empresa.descricao)
        statement.setString(5, empresa.pais)
        statement.setString(6, empresa.estado)
        statement.setString(7, empresa.cidade)
        statement.setString(8, empresa.cep)
        statement.setString(9, empresa.senha)
        statement.executeUpdate()
        ResultSet resultado = statement.getGeneratedKeys()

        int idGerado = 0
        if (resultado.next()) {
            idGerado = resultado.getInt(1)
        }
        connection.close()
        return idGerado
    }

    Empresa buscarEmpresaPorId(int idEmpresa) {
        Connection connection = conectar()
        String sql = "SELECT * FROM empresa WHERE id = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setInt(1, idEmpresa)
        ResultSet resultado = statement.executeQuery()
        Empresa empresa = null
        if (resultado.next()) {
            empresa = criarEmpresa(resultado)
        }
        connection.close()
        return empresa
    }

    Empresa buscarEmpresaPorCnpj(String cnpjEmpresa) {
        Connection connection = conectar()
        String sql = "SELECT * FROM empresa WHERE cnpj = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, cnpjEmpresa)
        ResultSet resultado = statement.executeQuery()
        Empresa empresa = null
        if (resultado.next()) {
            empresa = criarEmpresa(resultado)
        }
        connection.close()
        return empresa
    }

    List<Empresa> listarEmpresas() {
        Connection connection = conectar()
        String sql = "SELECT * FROM empresa"
        PreparedStatement statement = connection.prepareStatement(sql)
        ResultSet resultado = statement.executeQuery()
        List<Empresa> empresas = []
        while (resultado.next()) {
            Empresa empresa = criarEmpresa(resultado)
            empresas.add(empresa)
        }
        connection.close()
        return empresas
    }

    boolean atualizarEmpresa(Empresa empresa) {
        Connection connection = conectar()
        String sql = "UPDATE empresa SET nome = ?, email = ?, descricao = ?, pais = ?, estado = ?, cidade = ?, cep = ?, senha = ? WHERE cnpj = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, empresa.nome)
        statement.setString(2, empresa.email)
        statement.setString(3, empresa.descricao)
        statement.setString(4, empresa.pais)
        statement.setString(5, empresa.estado)
        statement.setString(6, empresa.cidade)
        statement.setString(7, empresa.cep)
        statement.setString(8, empresa.senha)
        statement.setString(9, empresa.cnpj)
        int linhasAtualizadas = statement.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarEmpresa(String cnpjEmpresa) {
        Connection connection = conectar()
        String sql = "DELETE FROM empresa WHERE cnpj = ?"
        PreparedStatement statement = connection.prepareStatement(sql)
        statement.setString(1, cnpjEmpresa)
        int linhasAfetadas = statement.executeUpdate()
        connection.close()
        return linhasAfetadas > 0
    }

}
