package org.alan.service

import org.alan.model.Candidato
import org.alan.model.CandidatoCurtidas
import org.alan.model.Vaga

class CandidatoCurtidasService {

    List<CandidatoCurtidas> curtidas = []
    VagaService vagaService;
    CandidatoService candidatoService;

    CandidatoCurtidasService(List<CandidatoCurtidas> curtidas, VagaService vagaService, CandidatoService candidatoService) {
        this.curtidas = curtidas
        this.vagaService = vagaService
        this.candidatoService = candidatoService
    }

    void curtirVaga(int vagaId, String candidatoCpf){
        Vaga vaga = vagaService.buscarVagaPorId(vagaId)
        Candidato candidato = candidatoService.buscarPorCpf(candidatoCpf)
        int index = curtidas.findIndexOf { it.candidato.cpf == candidatoCpf }
        if (index != -1) {
            curtidas[index].vagasCurtidas.add(vaga)
        } else {
            curtidas.add(new CandidatoCurtidas(candidato: candidato, vagasCurtidas: [vaga]))
        }
    }

}
