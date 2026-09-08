package org.alan.service

import org.alan.model.Candidato

class CandidatoService {

    List<Candidato> list


    CandidatoService(List<Candidato> list) {
        this.list = list;
    }


    List<Candidato> salvar(Candidato candidatoRequest) {
        list.add(candidatoRequest)
        return list;
    }

    Candidato buscarPorCpf(String cpf) {
        Candidato candidato = list.find { it.cpf == cpf }
        if (candidato == null){
            throw new Exception("Candidato não encontrado")
        }
        return candidato;
    }
}

