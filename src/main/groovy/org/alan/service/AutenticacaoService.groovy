package org.alan.service

import org.alan.model.Candidato
import org.alan.model.Empresa
import org.alan.model.Recrutador

class AutenticacaoService {

    private final CandidatoService candidatoService
    private final EmpresaService empresaService
    private final RecrutadorService recrutadorService
    private SessaoAutenticada sessao

    AutenticacaoService(
            CandidatoService candidatoService,
            EmpresaService empresaService,
            RecrutadorService recrutadorService
    ) {
        this.candidatoService = candidatoService
        this.empresaService = empresaService
        this.recrutadorService = recrutadorService
    }

    SessaoAutenticada entrar(String identificador, String senha) {
        Candidato candidato = localizarCandidato(identificador)
        Empresa empresa = localizarEmpresa(identificador)
        Recrutador recrutador = localizarRecrutador(identificador)

        if (candidato && recrutador) {
            throw new Exception("CPF cadastrado para mais de um tipo de usuário")
        }

        List<SessaoAutenticada> correspondencias = []
        if (candidato?.senha == senha) {
            correspondencias.add(new SessaoAutenticada("CANDIDATO", candidato, identificador))
        }
        if (empresa?.senha == senha) {
            correspondencias.add(new SessaoAutenticada("EMPRESA", empresa, identificador))
        }
        if (recrutador?.senha == senha) {
            if (recrutador.status != "ATIVO") {
                throw new Exception("Recrutador sem vínculo ativo")
            }
            correspondencias.add(new SessaoAutenticada("RECRUTADOR", recrutador, identificador))
        }

        if (correspondencias.isEmpty()) {
            throw new Exception("Credenciais inválidas")
        }
        if (correspondencias.size() > 1) {
            throw new Exception("Credenciais ambíguas para mais de um tipo de usuário")
        }

        sessao = correspondencias.first()
        return sessao
    }

    SessaoAutenticada obterSessao() {
        return sessao
    }

    void sair() {
        sessao = null
    }

    private Candidato localizarCandidato(String cpf) {
        try {
            return candidatoService.buscarPorCpf(cpf)
        } catch (Exception ignored) {
            return null
        }
    }

    private Empresa localizarEmpresa(String cnpj) {
        try {
            return empresaService.buscarPorCnpj(cnpj)
        } catch (Exception ignored) {
            return null
        }
    }

    private Recrutador localizarRecrutador(String cpf) {
        try {
            return recrutadorService.buscarPorCpf(cpf)
        } catch (Exception ignored) {
            return null
        }
    }
}
