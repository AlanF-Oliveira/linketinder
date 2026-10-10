package org.alan.controller

import org.alan.model.Candidato
import org.alan.service.CandidatoService

class CandidatoController {

    private final CandidatoService candidatoService

    CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService
    }

    List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos()
    }

    Candidato salvar(Candidato candidato) {
        return candidatoService.salvar(candidato)
    }

    Candidato buscarPorCpf(String cpf) {
        return candidatoService.buscarPorCpf(cpf)
    }


    Candidato atualizarCandidato(Candidato candidato) {
        return candidatoService.atualizarCandidato(candidato)
    }

    void deletarCandidato(String cpf) {
        candidatoService.deletarCandidato(cpf)
    }
}