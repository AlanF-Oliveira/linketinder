package org.alan.service

import org.alan.model.Vaga
import org.alan.repository.VagaRepository

class VagaService {

    private final VagaRepository vagaRepository

    VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository
    }

    Vaga criarVaga(Vaga vaga, int idEmpresa) {
        validarEstado(vaga.estado)
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

    List<Vaga> listarVagasDaEmpresa(int idEmpresa) {
        return vagaRepository.listarVagasDaEmpresa(idEmpresa)
    }

    Vaga buscarVagaPorId(int idVaga) {
        Vaga vaga = vagaRepository.buscarVagaPorId(idVaga)

        if (vaga == null) {
            throw new Exception("Vaga não encontrada")
        }

        return vaga
    }

    Vaga atualizarVaga(Vaga vaga) {
        validarEstado(vaga.estado)
        boolean atualizou = vagaRepository.atualizarVaga(vaga)

        if (!atualizou) {
            throw new Exception("Vaga não encontrada")
        }

        return vaga
    }

    Vaga atualizarVagaDaEmpresa(Vaga vaga, int idEmpresa) {
        validarEstado(vaga.estado)
        if (!vagaRepository.atualizarVagaDaEmpresa(vaga, idEmpresa)) {
            throw new Exception("Vaga não encontrada para esta empresa")
        }
        return vaga
    }

    private static void validarEstado(String estado) {
        if (estado && estado.trim().size() != 2) {
            throw new Exception("Estado inválido: informe a UF com 2 letras, por exemplo CE")
        }
    }

    void deletarVaga(int idVaga) {
        boolean deletou = vagaRepository.deletarVaga(idVaga)

        if (!deletou) {
            throw new Exception("Vaga não encontrada")
        }
    }

    void deletarVagaDaEmpresa(int idVaga, int idEmpresa) {
        if (!vagaRepository.deletarVagaDaEmpresa(idVaga, idEmpresa)) {
            throw new Exception("Vaga não encontrada para esta empresa")
        }
    }
}