package org.alan.service

import org.alan.model.Candidato
import org.alan.model.CandidatoCurtido
import org.alan.model.Empresa
import org.alan.model.EmpresaCurtidas
import org.alan.model.Vaga

class EmpresaCurtidasService {

    List<EmpresaCurtidas> curtidas = [];
    EmpresaService empresaService;
    CandidatoService candidatoService;
    VagaService vagaService;

    EmpresaCurtidasService(List<EmpresaCurtidas> curtidas, EmpresaService empresaService, CandidatoService candidatoService, VagaService vagaService) {
        this.curtidas = curtidas
        this.empresaService = empresaService
        this.candidatoService = candidatoService
        this.vagaService = vagaService
    }

    void curtirCandidato(int vagaId, String empresaCnpj, String candidatoCpf) {
        Empresa empresa = empresaService.buscarPorCnpj(empresaCnpj);
        Candidato candidato = candidatoService.buscarPorCpf(candidatoCpf);
        Vaga vaga = vagaService.buscarVagaPorId(vagaId)
        int index = curtidas.findIndexOf { it.empresa.cnpj == empresaCnpj }
        if (index != -1) {
            curtidas[index].candidatoCurtidos.add(new CandidatoCurtido(candidato: candidato, vaga: vaga))
        } else{
            curtidas.add(new EmpresaCurtidas(
                    empresa: empresa,
                    candidatoCurtidos: [new CandidatoCurtido(candidato: candidato, vaga: vaga)]))
        }
    }
}


