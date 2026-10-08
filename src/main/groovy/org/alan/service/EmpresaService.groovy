package org.alan.service


import org.alan.model.Empresa
import org.alan.repository.EmpresaRepository

class EmpresaService {


    private final EmpresaRepository empresaRepository

    EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository
    }

    Empresa salvar(Empresa empresa) {
        int idGerado = empresaRepository.inserir(empresa)

        if (idGerado == 0) {
            throw new Exception("Falha ao cadastrar empresa")
        }

        empresa.id = idGerado
        return empresa
    }

    Empresa buscarPorCnpj(String cnpj) {
        Empresa empresa = empresaRepository.buscarEmpresaPorCnpj(cnpj)
        if (empresa == null) {
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    List<Empresa> listarEmpresas() {
        return empresaRepository.listarEmpresas()
    }

    Empresa atualizarEmpresa(Empresa empresa) {
        boolean atualizou = empresaRepository.atualizarEmpresa(empresa)
        if (!atualizou) {
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    void deletarEmpresa(String cnpj) {
        boolean deletou = empresaRepository.deletarEmpresa(cnpj)
        if (!deletou) {
            throw new Exception("Empresa não encontrada")
        }
    }
}