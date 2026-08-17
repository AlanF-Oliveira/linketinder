package org.alan.service

import org.alan.model.Candidato

class CandidatoService{

    List<Candidato> list


    CandidatoService(List<Candidato> list) {
        this.list = list
    }



    List<Candidato> salvar (Candidato candidatoRequest){
        list.add(candidatoRequest)
        return list
    }
}

