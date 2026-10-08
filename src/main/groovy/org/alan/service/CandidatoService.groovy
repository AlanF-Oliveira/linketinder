package org.alan.service


import org.alan.model.Candidato
import org.alan.repository.CandidatoRepository

class CandidatoService {


    private final CandidatoRepository candidatoRepository

    CandidatoService(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository
    }

    Candidato salvar(Candidato candidato) {
        int idGerado = candidatoRepository.inserir(candidato)
        if (idGerado == 0) {
            throw new Exception("Falha ao cadastrar candidato")
        }

        candidato.id = idGerado
        return candidato
    }

    Candidato buscarPorCpf(String cpf) {
        Candidato candidato = candidatoRepository.buscarCandidatoPorCpf(cpf)
        if (candidato == null) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    List<Candidato> listarCandidatos() {
        return candidatoRepository.listarCandidatos()
    }

    Candidato atualizarCandidato(Candidato candidato) {
        boolean atualizou = candidatoRepository.atualizarCandidato(candidato)
        if (!atualizou) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    void deletarCandidato(String cpf) {
        boolean deletou = candidatoRepository.deletarCandidato(cpf)
        if (!deletou) {
            throw new Exception("Candidato não encontrado")
        }
    }
}
