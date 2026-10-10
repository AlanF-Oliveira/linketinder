package org.alan.controller

import org.alan.model.Empresa
import org.alan.service.EmpresaService

class EmpresaController {

    private final EmpresaService empresaService

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    Empresa salvar(Empresa empresa) {
        return empresaService.salvar(empresa)
    }

    Empresa buscarPorCnpj(String cnpj) {
        return empresaService.buscarPorCnpj(cnpj)
    }

    List<Empresa> listarEmpresas() {
        return empresaService.listarEmpresas()
    }

    Empresa atualizarEmpresa(Empresa empresa) {
        return empresaService.atualizarEmpresa(empresa)
    }

    void deletarEmpresa(String cnpj) {
        empresaService.deletarEmpresa(cnpj)
    }
}