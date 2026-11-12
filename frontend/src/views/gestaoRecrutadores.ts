import { AuthService } from '../services/AuthService';
import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { Recrutador } from '../models/Recrutador';
import { RecrutadorService } from '../services/RecrutadorService';
import { VagaService } from '../services/VagaService';
import { criarAreaEmpresa } from './areaEmpresa';

export function criarGestaoRecrutadores(
    app: HTMLElement,
    empresaService: EmpresaService,
    candidatoService: CandidatoService,
    vagaService: VagaService,
    recrutadorService: RecrutadorService,
    authService: AuthService
): void {
    const sessao = authService.obterSessao();
    if (!sessao || sessao.tipo !== 'empresa') {
        throw new Error('Somente a empresa autenticada pode gerenciar recrutadores.');
    }
    const empresa = sessao.usuario;
    const recrutadores = recrutadorService.listarPorEmpresa(empresa.cnpj);
    app.innerHTML = `
      <div class="container mt-4">
        <button id="btn-voltar" class="btn btn-outline-secondary mb-3">Voltar</button>
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h2>Recrutadores de ${empresa.nome}</h2>
          <button id="btn-novo" class="btn btn-primary">Cadastrar recrutador</button>
        </div>
        <div class="row g-3">
          ${recrutadores.map(recrutador => `
            <div class="col-md-6"><div class="card h-100"><div class="card-body">
              <h5>${recrutador.nome}</h5>
              <p class="mb-1">${recrutador.areaAtuacao}</p>
              <span class="badge ${recrutador.status === 'ATIVO' ? 'text-bg-success' : 'text-bg-secondary'}">${recrutador.status}</span>
              <div class="mt-3 d-flex gap-2"><button class="btn btn-sm btn-outline-primary btn-editar" data-cpf="${recrutador.cpf}">Editar</button><button class="btn btn-sm btn-outline-danger btn-remover" data-cpf="${recrutador.cpf}">Remover</button></div>
            </div></div></div>`).join('')}
        </div>
        <div id="form-recrutador" class="mt-4"></div>
      </div>
    `;
    document.getElementById('btn-voltar')?.addEventListener('click', () =>
        criarAreaEmpresa(app, empresaService, candidatoService, vagaService, recrutadorService, authService)
    );
    document.getElementById('btn-novo')?.addEventListener('click', () => renderizarFormulario(
        app, empresaService, candidatoService, vagaService, recrutadorService, authService
    ));
    document.querySelectorAll('.btn-remover').forEach(button => button.addEventListener('click', () => {
        recrutadorService.removerRecrutador(String((button as HTMLElement).dataset.cpf), empresa.cnpj);
        criarGestaoRecrutadores(app, empresaService, candidatoService, vagaService, recrutadorService, authService);
    }));
    document.querySelectorAll('.btn-editar').forEach(button => button.addEventListener('click', () => {
        const recrutador = recrutadorService.listarRecrutadores().find(item =>
            item.cpf === String((button as HTMLElement).dataset.cpf)
        );
        if (!recrutador) {
            return;
        }
        const nome = window.prompt('Nome', recrutador.nome);
        const area = window.prompt('Área de atuação', recrutador.areaAtuacao);
        if (nome && area) {
            recrutador.nome = nome;
            recrutador.areaAtuacao = area;
            criarGestaoRecrutadores(app, empresaService, candidatoService, vagaService, recrutadorService, authService);
        }
    }));
}

function renderizarFormulario(
    app: HTMLElement,
    empresaService: EmpresaService,
    candidatoService: CandidatoService,
    vagaService: VagaService,
    recrutadorService: RecrutadorService,
    authService: AuthService
): void {
    const sessao = authService.obterSessao();
    if (!sessao || sessao.tipo !== 'empresa') {
        return;
    }
    const container = document.getElementById('form-recrutador') as HTMLElement;
    container.innerHTML = `
      <h3>Novo recrutador</h3>
      <form id="form-novo-recrutador">
        <div class="row g-2">
          <div class="col-md-6"><input name="nome" class="form-control" placeholder="Nome" required></div>
          <div class="col-md-6"><input name="area" class="form-control" placeholder="Área de atuação" required></div>
          <div class="col-md-6"><input name="email" type="email" class="form-control" placeholder="E-mail" required></div>
          <div class="col-md-6"><input name="cpf" class="form-control" placeholder="CPF" required></div>
          <div class="col-md-6"><input name="nascimento" type="date" class="form-control" required></div>
          <div class="col-md-6"><input name="senha" type="password" class="form-control" placeholder="Senha" required></div>
          <div class="col-12"><textarea name="descricao" class="form-control" placeholder="Descrição" required></textarea></div>
        </div>
        <button class="btn btn-success mt-3" type="submit">Salvar</button>
      </form>
    `;
    document.getElementById('form-novo-recrutador')?.addEventListener('submit', event => {
        event.preventDefault();
        const dados = new FormData(event.currentTarget as HTMLFormElement);
        try {
            recrutadorService.salvarRecrutador(new Recrutador(
                String(dados.get('nome')),
                String(dados.get('area')),
                String(dados.get('descricao')),
                String(dados.get('email')),
                String(dados.get('cpf')),
                String(dados.get('nascimento')),
                String(dados.get('senha')),
                sessao.usuario
            ));
            criarGestaoRecrutadores(app, empresaService, candidatoService, vagaService, recrutadorService, authService);
        } catch (error) {
            alert(error instanceof Error ? error.message : 'Erro ao cadastrar recrutador.');
        }
    });
}
