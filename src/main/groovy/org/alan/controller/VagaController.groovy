package org.alan.controller

import org.alan.model.Empresa
import org.alan.model.Vaga
import org.alan.service.EmpresaService
import org.alan.service.VagaService

class VagaController {

    private final VagaService vagaService
    private final EmpresaService empresaService

    VagaController(VagaService vagaService, EmpresaService empresaService) {
        this.vagaService = vagaService
        this.empresaService = empresaService
    }

    List<Vaga> listarVagas() {
        return vagaService.listarVagas()
    }

    Vaga criarVaga(Vaga vaga, String cnpj) {
        Empresa empresa = empresaService.buscarPorCnpj(cnpj)
        return vagaService.criarVaga(vaga, empresa.id)
    }

    Vaga buscarVagaPorId(int id) {
        return vagaService.buscarVagaPorId(id)
    }

    Vaga atualizarVaga(Vaga vaga) {
        return vagaService.atualizarVaga(vaga)
    }

    void deletarVaga(int id) {
        vagaService.deletarVaga(id)
    }
}