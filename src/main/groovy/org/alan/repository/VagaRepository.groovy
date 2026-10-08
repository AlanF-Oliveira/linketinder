package org.alan.repository

import org.alan.model.Vaga

interface VagaRepository {

    int inserir(Vaga vaga, int idEmpresa)

    Vaga buscarVagaPorId(int idVaga)

    List<Vaga> listarVagas()

    boolean atualizarVaga(Vaga vaga)

    boolean deletarVaga(int idVaga)
}