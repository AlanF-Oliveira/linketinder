package org.alan.service


import org.alan.model.Empresa
import org.alan.repository.EmpresaRepository

class EmpresaService {


    private final EmpresaRepository empresaRepository

    EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository
    }

    Empresa salvar(Empresa empresa) {
        validarLocalizacao(empresa.estado, empresa.cep)
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
        validarLocalizacao(empresa.estado, empresa.cep)
        boolean atualizou = empresaRepository.atualizarEmpresa(empresa)
        if (!atualizou) {
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    private static void validarLocalizacao(String estado, String cep) {
        if (estado && estado.trim().size() != 2) {
            throw new Exception("Estado inválido: informe a UF com 2 letras, por exemplo CE")
        }
        if (cep && cep.size() > 9) {
            throw new Exception("CEP inválido: informe no máximo 9 caracteres")
        }
    }

    void deletarEmpresa(String cnpj) {
        boolean deletou = empresaRepository.deletarEmpresa(cnpj)
        if (!deletou) {
            throw new Exception("Empresa não encontrada")
        }
    }
}