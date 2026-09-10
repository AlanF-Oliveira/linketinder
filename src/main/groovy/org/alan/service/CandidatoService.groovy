package org.alan.service

import org.alan.database.BancoDeDados
import org.alan.model.Candidato

class CandidatoService {

    BancoDeDados bd;

    CandidatoService(BancoDeDados bd) {
        this.bd = bd;
    }

    Candidato salvar(Candidato candidato) {
        bd.insertCandidato(candidato)
        if (candidato.id == null) {
            throw new Exception("Falha ao cadastrar candidato")
        }
        return candidato
    }

    Candidato buscarPorCpf(String cpf) {
        Candidato candidato = bd.buscarCandidatoPorCpf(cpf)
        if (candidato == null) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    List<Candidato> listarCandidatos() {
        return bd.listarCandidatos()
    }

    Candidato atualizarCandidato(Candidato candidato) {
        boolean atualizou = bd.atualizarCandidato(candidato)
        if (!atualizou) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    void deletarCandidato(String cpf) {
        boolean deletou = bd.deletarCandidato(cpf)
        if(!deletou){
            throw new Exception("Candidato não encontrado")
        }
    }
}
