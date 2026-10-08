package org.alan.repository

import org.alan.model.Candidato

interface CandidatoRepository {

    int inserir(Candidato candidato)

    Candidato buscarCandidatoPorCpf(String cpf)

    List<Candidato> listarCandidatos()

    boolean atualizarCandidato(Candidato candidato)

    boolean deletarCandidato(String cpfCandidato)
}