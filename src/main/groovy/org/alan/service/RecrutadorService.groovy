package org.alan.service

import org.alan.model.Recrutador
import org.alan.repository.EmpresaRepository
import org.alan.repository.RecrutadorRepository

class RecrutadorService {

    private final RecrutadorRepository recrutadorRepository
    private final EmpresaRepository empresaRepository

    RecrutadorService(
            RecrutadorRepository recrutadorRepository,
            EmpresaRepository empresaRepository
    ) {
        this.recrutadorRepository = recrutadorRepository
        this.empresaRepository = empresaRepository
    }

    Recrutador salvar(Recrutador recrutador, String cnpjEmpresa) {
        validar(recrutador)
        def empresa = empresaRepository.buscarEmpresaPorCnpj(cnpjEmpresa)
        if (empresa == null) {
            throw new Exception("Empresa não encontrada")
        }
        if (recrutadorRepository.buscarPorCpf(recrutador.cpf) != null) {
            throw new Exception("Recrutador com CPF já cadastrado")
        }
        int id = recrutadorRepository.inserir(recrutador, empresa.id)
        if (id == 0) {
            throw new Exception("Falha ao cadastrar recrutador")
        }
        recrutador.id = id
        recrutador.empresa = empresa
        recrutador.status = recrutador.status ?: "ATIVO"
        return recrutador
    }

    Recrutador buscarPorCpf(String cpf) {
        def recrutador = recrutadorRepository.buscarPorCpf(cpf)
        if (recrutador == null) {
            throw new Exception("Recrutador não encontrado")
        }
        return recrutador
    }

    Recrutador buscarPorCpfNaEmpresa(String cpf, String cnpjEmpresa) {
        def recrutador = recrutadorRepository.buscarPorCpfNaEmpresa(cpf, cnpjEmpresa)
        if (recrutador == null) {
            throw new Exception("Recrutador não encontrado para esta empresa")
        }
        return recrutador
    }

    List<Recrutador> listarPorEmpresa(String cnpjEmpresa) {
        if (empresaRepository.buscarEmpresaPorCnpj(cnpjEmpresa) == null) {
            throw new Exception("Empresa não encontrada")
        }
        return recrutadorRepository.listarPorEmpresa(cnpjEmpresa)
    }

    Recrutador atualizar(Recrutador recrutador, String cnpjEmpresa) {
        validar(recrutador)
        buscarPorCpfNaEmpresa(recrutador.cpf, cnpjEmpresa)
        if (!recrutadorRepository.atualizar(recrutador, cnpjEmpresa)) {
            throw new Exception("Recrutador não encontrado para esta empresa")
        }
        return recrutador
    }

    void deletar(String cpf, String cnpjEmpresa) {
        buscarPorCpfNaEmpresa(cpf, cnpjEmpresa)
        if (!recrutadorRepository.deletar(cpf, cnpjEmpresa)) {
            throw new Exception("Recrutador não encontrado para esta empresa")
        }
    }

    boolean possuiVinculoAtivo(String cpf, String cnpjEmpresa) {
        return recrutadorRepository.possuiVinculoAtivo(cpf, cnpjEmpresa)
    }

    private static void validar(Recrutador recrutador) {
        if (!recrutador.nome || recrutador.nome.size() > 50 ||
                !recrutador.areaAtuacao || recrutador.areaAtuacao.size() > 50 ||
                !recrutador.descricao || recrutador.descricao.size() > 100 ||
                !recrutador.email || recrutador.email.size() > 50 ||
                !recrutador.cpf || recrutador.cpf.size() > 12 ||
                !recrutador.nascimento || !recrutador.senha ||
                recrutador.senha.size() < 6 || recrutador.senha.size() > 50) {
            throw new Exception("Dados do recrutador inválidos")
        }
    }
}
