package org.alan.repository

import org.alan.model.Vaga

interface VagaRepository {

    int inserir(Vaga vaga, int idEmpresa)

    Vaga buscarVagaPorId(int idVaga)

    List<Vaga> listarVagas()

    List<Vaga> listarVagasDaEmpresa(int idEmpresa)

    boolean atualizarVaga(Vaga vaga)

    boolean atualizarVagaDaEmpresa(Vaga vaga, int idEmpresa)

    boolean deletarVaga(int idVaga)

    boolean deletarVagaDaEmpresa(int idVaga, int idEmpresa)
}