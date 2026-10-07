package org.alan.service

import org.alan.dao.EmpresaDAO
import org.alan.model.Empresa

class EmpresaService {

    private final EmpresaDAO empresaDAO

    EmpresaService(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    Empresa salvar(Empresa empresa) {
        int idGerado = empresaDAO.inserir(empresa)

        if (idGerado == 0) {
            throw new Exception("Falha ao cadastrar empresa")
        }

        empresa.id = idGerado
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

    Empresa atualizarEmpresa(Empresa empresa) {
        boolean atualizou = empresaDAO.atualizarEmpresa(empresa)
        if (!atualizou) {
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