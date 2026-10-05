package org.alan.dao

import org.alan.database.ConnectionFactory
import org.alan.model.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class EmpresaDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory()

    private Connection conectar() {
        return connectionFactory.criarConexao()
    }


    int insertEmpresa(Empresa empresa) {
        Connection connection = conectar()
        String sql = "INSERT INTO empresa (nome, email, cnpj, descricao, pais, estado, cidade, cep, senha)" +
                " VALUES(?, ? ,? ,? ,? ,?, ? ,? ,?)"
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ps.setString(1, empresa.nome)
        ps.setString(2, empresa.email)
        ps.setString(3, empresa.cnpj)
        ps.setString(4, empresa.descricao)
        ps.setString(5, empresa.pais)
        ps.setString(6, empresa.estado)
        ps.setString(7, empresa.cidade)
        ps.setString(8, empresa.cep)
        ps.setString(9, empresa.senha)
        ps.executeUpdate()
        ResultSet rs = ps.getGeneratedKeys()

        int idGerado = 0
        if (rs.next()) {
            idGerado = rs.getInt(1)
            empresa.id = idGerado
        }
        connection.close()
        return idGerado
    }

    Empresa buscarEmpresaPorId(int idEmpresa) {
        Connection connection = conectar()
        String sql = "SELECT * FROM empresa WHERE id = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idEmpresa)
        ResultSet rs = ps.executeQuery()
        Empresa empresa = null
        if (rs.next()) {
            empresa = new Empresa(
                    id: rs.getInt("id"),
                    nome: rs.getString("nome"),
                    email: rs.getString("email"),
                    cnpj: rs.getString("cnpj"),
                    cidade: rs.getString("cidade"),
                    estado: rs.getString("estado"),
                    pais: rs.getString("pais"),
                    cep: rs.getString("cep"),
                    descricao: rs.getString("descricao"),
                    senha: rs.getString("senha")
            )
        }
        connection.close()
        return empresa
    }

    Empresa buscarEmpresaPorCnpj(String cnpjEmpresa) {

        Connection connection = conectar()
        String sql = "SELECT * FROM empresa WHERE cnpj = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cnpjEmpresa)
        ResultSet rs = ps.executeQuery()
        Empresa empresa = null
        if (rs.next()) {
            empresa = new Empresa(
                    id: rs.getInt("id"),
                    nome: rs.getString("nome"),
                    email: rs.getString("email"),
                    cnpj: rs.getString("cnpj"),
                    cidade: rs.getString("cidade"),
                    estado: rs.getString("estado"),
                    pais: rs.getString("pais"),
                    cep: rs.getString("cep"),
                    descricao: rs.getString("descricao"),
                    senha: rs.getString("senha")
            )
        }
        connection.close()
        return empresa
    }

    List<Empresa> listarEmpresas() {
        Connection connection = conectar()
        String sql = "SELECT * FROM empresa"
        PreparedStatement ps = connection.prepareStatement(sql)
        ResultSet rs = ps.executeQuery()
        List<Empresa> empresas = []
        while (rs.next()) {
            Empresa empresa = new Empresa(
                    id: rs.getInt("id"),
                    nome: rs.getString("nome"),
                    email: rs.getString("email"),
                    cnpj: rs.getString("cnpj"),
                    cidade: rs.getString("cidade"),
                    estado: rs.getString("estado"),
                    pais: rs.getString("pais"),
                    cep: rs.getString("cep"),
                    descricao: rs.getString("descricao"),
                    senha: rs.getString("senha")
            )
            empresas.add(empresa)
        }
        connection.close()
        return empresas
    }

    boolean atualizarEmpresa(Empresa empresa) {
        Connection connection = conectar()
        String sql = "UPDATE empresa SET nome = ?, email = ?, descricao = ?, pais = ?, estado = ?, cidade = ?, cep = ?, senha = ? WHERE cnpj = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, empresa.getNome())
        ps.setString(2, empresa.email)
        ps.setString(3, empresa.descricao)
        ps.setString(4, empresa.pais)
        ps.setString(5, empresa.estado)
        ps.setString(6, empresa.cidade)
        ps.setString(7, empresa.cep)
        ps.setString(8, empresa.senha)
        ps.setString(9, empresa.cnpj)
        int linhasAtualizadas = ps.executeUpdate()
        connection.close()
        return linhasAtualizadas > 0
    }

    boolean deletarEmpresa(String cnpjEmpresa) {

        Connection connection = conectar()
        String sql = "SELECT id FROM empresa WHERE cnpj = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, cnpjEmpresa)
        ResultSet rs = ps.executeQuery()
        if (!rs.next()) {
            connection.close()
            return false
        }
        int idEmpresa = rs.getInt("id")
        connection.close()
        String sqlBuscarVagas = "SELECT id FROM vagas WHERE id_empresa = ?"
        Connection connectionVagas = conectar()
        PreparedStatement psVagas = connectionVagas.prepareStatement(sqlBuscarVagas)
        psVagas.setInt(1, idEmpresa)
        ResultSet rsVagas = psVagas.executeQuery()
        List<Integer> idsVagas = []
        while (rsVagas.next()) {
            idsVagas.add(rsVagas.getInt("id"))
        }
        connectionVagas.close()
        idsVagas.each { idVaga ->
            deletarVaga(idVaga)
        }
        Connection connectionEmpresa = conectar()
        String sqlDelEmpresa = "DELETE FROM empresa WHERE cnpj = ?"
        PreparedStatement psEmpresa = connectionEmpresa.prepareStatement(sqlDelEmpresa)
        psEmpresa.setString(1, cnpjEmpresa)
        int linhasAtualizadas = psEmpresa.executeUpdate()
        connectionEmpresa.close()
        return linhasAtualizadas > 0
    }

}
