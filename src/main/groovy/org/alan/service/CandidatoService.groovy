package org.alan.service

import org.alan.dao.CandidatoDAO
import org.alan.model.Candidato

class CandidatoService {


    private final CandidatoDAO candidatoDAO

    CandidatoService(CandidatoDAO candidatoDAO) {
        this.candidatoDAO = candidatoDAO
    }

    Candidato salvar(Candidato candidato) {
        int idGerado = candidatoDAO.inserir(candidato)

        if (idGerado == 0) {
            throw new Exception("Falha ao cadastrar candidato")
        }

        candidato.id = idGerado
        return candidato
    }

    Candidato buscarPorCpf(String cpf) {
        Candidato candidato = candidatoDAO.buscarCandidatoPorCpf(cpf)
        if (candidato == null) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    List<Candidato> listarCandidatos() {
        return candidatoDAO.listarCandidatos()
    }

    Candidato atualizarCandidato(Candidato candidato) {
        boolean atualizou = candidatoDAO.atualizarCandidato(candidato)
        if (!atualizou) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    void deletarCandidato(String cpf) {
        boolean deletou = candidatoDAO.deletarCandidato(cpf)
        if(!deletou){
            throw new Exception("Candidato não encontrado")
        }
    }
}
