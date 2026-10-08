package org.alan.repository

import org.alan.model.Empresa

interface EmpresaRepository {

    int inserir(Empresa empresa)

    Empresa buscarEmpresaPorId(int idEmpresa)

    Empresa buscarEmpresaPorCnpj(String cnpjEmpresa)

    List<Empresa> listarEmpresas()

    boolean atualizarEmpresa(Empresa empresa)

    boolean deletarEmpresa(String cnpjEmpresa)
}