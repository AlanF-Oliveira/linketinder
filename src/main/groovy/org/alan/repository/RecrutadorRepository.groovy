package org.alan.repository

import org.alan.model.Recrutador

interface RecrutadorRepository {

    int inserir(Recrutador recrutador, int idEmpresa)

    Recrutador buscarPorCpf(String cpf)

    Recrutador buscarPorCpfNaEmpresa(String cpf, String cnpjEmpresa)

    boolean possuiVinculoAtivo(String cpf, String cnpjEmpresa)

    List<Recrutador> listarPorEmpresa(String cnpjEmpresa)

    boolean atualizar(Recrutador recrutador, String cnpjEmpresa)

    boolean deletar(String cpf, String cnpjEmpresa)
}
