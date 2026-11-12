package org.alan.controller

import org.alan.model.Recrutador
import org.alan.service.RecrutadorService

class RecrutadorController {

    private final RecrutadorService recrutadorService

    RecrutadorController(RecrutadorService recrutadorService) {
        this.recrutadorService = recrutadorService
    }

    Recrutador salvar(Recrutador recrutador, String cnpjEmpresa) {
        recrutadorService.salvar(recrutador, cnpjEmpresa)
    }

    Recrutador buscarPorCpf(String cpf) {
        recrutadorService.buscarPorCpf(cpf)
    }

    Recrutador buscarPorCpfNaEmpresa(String cpf, String cnpjEmpresa) {
        recrutadorService.buscarPorCpfNaEmpresa(cpf, cnpjEmpresa)
    }

    List<Recrutador> listarPorEmpresa(String cnpjEmpresa) {
        recrutadorService.listarPorEmpresa(cnpjEmpresa)
    }

    Recrutador atualizar(Recrutador recrutador, String cnpjEmpresa) {
        recrutadorService.atualizar(recrutador, cnpjEmpresa)
    }

    void deletar(String cpf, String cnpjEmpresa) {
        recrutadorService.deletar(cpf, cnpjEmpresa)
    }

    boolean possuiVinculoAtivo(String cpf, String cnpjEmpresa) {
        recrutadorService.possuiVinculoAtivo(cpf, cnpjEmpresa)
    }
}
