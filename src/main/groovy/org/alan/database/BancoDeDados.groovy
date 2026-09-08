package org.alan.database

import org.alan.model.Candidato
import org.alan.model.Empresa
import org.alan.model.Vaga

import java.sql.*

class BancoDeDados {

    private static final String URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String USUARIO = "postgres"
    private static final String SENHA = "2010"

    Connection conectar() {
        return DriverManager.getConnection(URL, USUARIO, SENHA)
    }

    //========================== Candidatos ==========================

    int insertCandidato(Candidato candidato) {
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
            candidato.id = idGerado
        }
        connection.close()
        candidato.competencias.each { nomeCompetencia ->
            int idCompetencia = buscarOuCriarCompetencia(nomeCompetencia)
            insertCandidatoCompetencia(idGerado, idCompetencia)
        }
        return idGerado
    }


    void insertCandidatoCompetencia(int idCandidato, int idCompetencia) {
        Connection connection = conectar()
        String sql = "INSERT INTO candidato_competencia (id_candidatos, id_competencias) VALUES (?,?)"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setInt(1, idCandidato)
        ps.setInt(2, idCompetencia)
        ps.executeUpdate()
        connection.close()
    }

    List<String> buscarCompetenciasDoCandidato(int idCandidato) {
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
            candidato = new Candidato(
                    id: rs.getInt("id"),
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
                    senha: rs.getString("senha"),
                    competencias: buscarCompetenciasDoCandidato(rs.getInt("id"))
            )
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
            Candidato candidato = new Candidato(
                    id: rs.getInt("id"),
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
                    senha: rs.getString("senha"),
                    competencias: buscarCompetenciasDoCandidato(rs.getInt("id"))
            )
            candidatos.add(candidato)
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

    //========================== Empresas ==========================

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



    //========================== Competencias ==========================

    Integer buscarCompetencia(String competencia) {
        Connection connection = conectar()
        String sql = "SELECT * FROM competencias WHERE competencia = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, competencia)
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
        ps.setString(1, competencia)
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
        String sql = "SELECT competencia FROM competencias"
        PreparedStatement ps = connection.prepareStatement(sql)
        ResultSet rs = ps.executeQuery()
        List<String> competencias = []
        while (rs.next()) {
            competencias.add(rs.getString("competencia"))
        }
        connection.close()
        return competencias
    }

    boolean atualizarCompetencia(String competencia, int idCompetencia) {
        Connection connection = conectar()
        String sql = "UPDATE competencias SET competencia = ? WHERE id = ?"
        PreparedStatement ps = connection.prepareStatement(sql)
        ps.setString(1, competencia)
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


    //========================== Vagas ==========================


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
            int idCompetencia = buscarOuCriarCompetencia(nomeCompetencia)
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
                    empresa: buscarEmpresaPorId(rs.getInt("id_empresa"))
            )
        }
        connection.close()
        return vaga
    }

    List<Vaga> listarVagas(){
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
                    empresa: buscarEmpresaPorId(rs.getInt("id_empresa"))
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
                int idCompetencia = buscarOuCriarCompetencia(nomeCompetencia)
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


