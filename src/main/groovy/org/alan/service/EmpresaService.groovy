package org.alan.service

import org.alan.database.BancoDeDados
import org.alan.model.Empresa

class EmpresaService {

    BancoDeDados bd;

    EmpresaService(BancoDeDados bd) {
        this.bd = bd;
    }

    Empresa salvar(Empresa empresa) {
        bd.insertEmpresa(empresa)
        if (empresa.id == null) {
                throw new Exception("Falha ao cadastrar empresa")
        }
        return empresa
    }

    Empresa buscarPorCnpj(String cnpj) {
        Empresa empresa = bd.buscarEmpresaPorCnpj(cnpj)
        if (empresa == null) {
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    List<Empresa> listarEmpresas() {
        return bd.listarEmpresas()
    }

    Empresa atualizarEmpresa(Empresa empresa){
       boolean atualizou =  bd.atualizarEmpresa(empresa)
        if (!atualizou){
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    void deletarEmpresa(String cnpj) {
        boolean deletou = bd.deletarEmpresa(cnpj)
        if (!deletou) {
            throw new Exception("Empresa não encontrada")
        }
    }
}