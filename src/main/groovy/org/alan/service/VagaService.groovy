package org.alan.service

import org.alan.model.Vaga
import org.alan.repository.VagaRepository

class VagaService {

    private final VagaRepository vagaRepository

    VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository
    }

    Vaga criarVaga(Vaga vaga, int idEmpresa) {
        int idGerado = vagaRepository.inserir(vaga, idEmpresa)

        if (idGerado == 0) {
            throw new Exception("Falha ao cadastrar vaga")
        }

        vaga.id = idGerado
        return vaga
    }

    List<Vaga> listarVagas() {
        return vagaRepository.listarVagas()
    }

    Vaga buscarVagaPorId(int idVaga) {
        Vaga vaga = vagaRepository.buscarVagaPorId(idVaga)

        if (vaga == null) {
            throw new Exception("Vaga não encontrada")
        }

        return vaga
    }

    Vaga atualizarVaga(Vaga vaga) {
        boolean atualizou = vagaRepository.atualizarVaga(vaga)

        if (!atualizou) {
            throw new Exception("Vaga não encontrada")
        }

        return vaga
    }

    void deletarVaga(int idVaga) {
        boolean deletou = vagaRepository.deletarVaga(idVaga)

        if (!deletou) {
            throw new Exception("Vaga não encontrada")
        }
    }
}