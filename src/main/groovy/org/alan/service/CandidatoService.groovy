package org.alan.service


import org.alan.model.Candidato
import org.alan.repository.CandidatoRepository

class CandidatoService {


    private final CandidatoRepository candidatoRepository

    CandidatoService(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository
    }

    Candidato salvar(Candidato candidato) {
        validarLocalizacao(candidato.estado, candidato.cep)
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
        validarLocalizacao(candidato.estado, candidato.cep)
        boolean atualizou = candidatoRepository.atualizarCandidato(candidato)
        if (!atualizou) {
            throw new Exception("Candidato não encontrado")
        }
        return candidato
    }

    private static void validarLocalizacao(String estado, String cep) {
        if (estado && estado.trim().size() != 2) {
            throw new Exception("Estado inválido: informe a UF com 2 letras, por exemplo CE")
        }
        if (cep && cep.size() > 9) {
            throw new Exception("CEP inválido: informe no máximo 9 caracteres")
        }
    }

    void deletarCandidato(String cpf) {
        boolean deletou = candidatoRepository.deletarCandidato(cpf)
        if (!deletou) {
            throw new Exception("Candidato não encontrado")
        }
    }
}
