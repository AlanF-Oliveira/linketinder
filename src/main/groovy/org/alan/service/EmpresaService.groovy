package org.alan.service

import org.alan.dao.EmpresaDAO
import org.alan.database.BancoDeDados
import org.alan.model.Empresa

class EmpresaService {

    private final EmpresaDAO empresaDAO

    EmpresaService(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    Empresa salvar(Empresa empresa) {
        empresaDAO.insertEmpresa(empresa)
        if (empresa.id == null) {
                throw new Exception("Falha ao cadastrar empresa")
        }
        return empresa
    }

    Empresa buscarPorCnpj(String cnpj) {
        Empresa empresa = empresaDAO.buscarEmpresaPorCnpj(cnpj)
        if (empresa == null) {
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    List<Empresa> listarEmpresas() {
        return empresaDAO.listarEmpresas()
    }

    Empresa atualizarEmpresa(Empresa empresa){
       boolean atualizou =  empresaDAO.atualizarEmpresa(empresa)
        if (!atualizou){
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    void deletarEmpresa(String cnpj) {
        boolean deletou = empresaDAO.deletarEmpresa(cnpj)
        if (!deletou) {
            throw new Exception("Empresa não encontrada")
        }
    }
}