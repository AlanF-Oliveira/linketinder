import { AuthService } from '../services/AuthService';
import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { RecrutadorService } from '../services/RecrutadorService';
import { VagaService } from '../services/VagaService';
import { Recrutador } from '../models/Recrutador';
import { Vaga } from '../models/Vaga';
import { criarTelaInicial } from './home';

export function criarAreaRecrutador(
    app: HTMLElement,
    recrutador: Recrutador,
    candidatoService: CandidatoService,
    empresaService: EmpresaService,
    recrutadorService: RecrutadorService,
    vagaService: VagaService,
    authService: AuthService
): void {
    const vagas = vagaService.listarVagas().filter(vaga => vaga.empresa.cnpj === recrutador.empresa.cnpj);
    const candidatos = candidatoService.listarCandidatos();
    const matches = recrutadorService.listarMatches(recrutador.empresa.cnpj);

    app.innerHTML = `
      <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <span class="badge text-bg-primary">Recrutador</span>
            <h2 class="mt-2 mb-1">${recrutador.nome}</h2>
            <p class="text-muted mb-0">${recrutador.areaAtuacao} · ${recrutador.empresa.nome}</p>
          </div>
          <button id="btn-sair" class="btn btn-outline-secondary">Sair</button>
        </div>
        <div class="row g-3 mb-4">
          <div class="col-md-4"><div class="card dashboard-card"><div class="card-body"><small>Vagas da empresa</small><strong>${vagas.length}</strong></div></div></div>
          <div class="col-md-4"><div class="card dashboard-card"><div class="card-body"><small>Candidatos</small><strong>${candidatos.length}</strong></div></div></div>
          <div class="col-md-4"><div class="card dashboard-card"><div class="card-body"><small>Matches</small><strong>${matches.length}</strong></div></div></div>
        </div>
        <div class="d-flex flex-wrap gap-2">
          <button id="btn-vagas" class="btn btn-primary">Gerenciar vagas</button>
          <button id="btn-candidatos" class="btn btn-outline-primary">Analisar candidatos</button>
          <button id="btn-matches" class="btn btn-outline-success">Ver matches</button>
        </div>
        <div id="painel-recrutador" class="mt-4"></div>
      </div>
    `;

    document.getElementById('btn-sair')?.addEventListener('click', () => {
        authService.logout();
        criarTelaInicial(app, candidatoService, empresaService, vagaService, authService, recrutadorService);
    });
    document.getElementById('btn-vagas')?.addEventListener('click', () =>
        renderizarVagas(app, recrutador, candidatoService, empresaService, recrutadorService, vagaService, authService)
    );
    document.getElementById('btn-candidatos')?.addEventListener('click', () =>
        renderizarCandidatos(recrutador, candidatoService, recrutadorService)
    );
    document.getElementById('btn-matches')?.addEventListener('click', () =>
        renderizarMatches(recrutador, recrutadorService)
    );
}

function renderizarVagas(
    app: HTMLElement,
    recrutador: Recrutador,
    candidatoService: CandidatoService,
    empresaService: EmpresaService,
    recrutadorService: RecrutadorService,
    vagaService: VagaService,
    authService: AuthService
): void {
    const painel = document.getElementById('painel-recrutador') as HTMLElement;
    const vagas = vagaService.listarVagas().filter(vaga => vaga.empresa.cnpj === recrutador.empresa.cnpj);
    painel.innerHTML = `
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Vagas de ${recrutador.empresa.nome}</h3>
        <button id="btn-nova-vaga" class="btn btn-success">Nova vaga</button>
      </div>
      <div class="row g-3">${vagas.map(vaga => `
        <div class="col-md-6"><div class="card h-100"><div class="card-body">
          <h5>${vaga.titulo}</h5><p>${vaga.descricao}</p>
          <small class="text-muted">${vaga.competenciasExigidas.join(', ')}</small>
          <div class="mt-3 d-flex gap-2"><button class="btn btn-sm btn-outline-primary btn-editar-vaga" data-id="${vaga.id}">Editar</button><button class="btn btn-sm btn-outline-danger btn-excluir-vaga" data-id="${vaga.id}">Excluir</button></div>
        </div></div></div>`).join('')}</div>
    `;
    document.getElementById('btn-nova-vaga')?.addEventListener('click', () => {
        painel.innerHTML = `
          <h3>Nova vaga</h3>
          <form id="form-vaga-recrutador">
            <input name="titulo" class="form-control mb-2" placeholder="Título" required>
            <textarea name="descricao" class="form-control mb-2" placeholder="Descrição" required></textarea>
            <input name="competencias" class="form-control mb-3" placeholder="Competências separadas por vírgula">
            <button class="btn btn-primary" type="submit">Salvar vaga</button>
          </form>`;
        document.getElementById('form-vaga-recrutador')?.addEventListener('submit', event => {
            event.preventDefault();
            const dados = new FormData(event.currentTarget as HTMLFormElement);
            vagaService.criarVaga(new Vaga(
                String(dados.get('titulo')),
                String(dados.get('descricao')),
                String(dados.get('competencias')).split(',').map(item => item.trim()).filter(Boolean),
                recrutador.empresa
            ));
            renderizarVagas(app, recrutador, candidatoService, empresaService, recrutadorService, vagaService, authService);
        });
    });
    painel.querySelectorAll('.btn-excluir-vaga').forEach(button => button.addEventListener('click', () => {
        vagaService.deletarVaga(Number((button as HTMLElement).dataset.id));
        renderizarVagas(app, recrutador, candidatoService, empresaService, recrutadorService, vagaService, authService);
    }));
    painel.querySelectorAll('.btn-editar-vaga').forEach(button => button.addEventListener('click', () => {
        const vaga = vagaService.listarVagas().find(item => item.id === Number((button as HTMLElement).dataset.id));
        if (!vaga) {
            return;
        }
        const titulo = window.prompt('Título da vaga', vaga.titulo);
        const descricao = window.prompt('Descrição da vaga', vaga.descricao);
        if (titulo && descricao) {
            vagaService.alterarVaga(vaga.id, new Vaga(titulo, descricao, vaga.competenciasExigidas, recrutador.empresa));
            const atualizada = vagaService.listarVagas().find(item => item.id === vaga.id);
            if (atualizada) {
                atualizada.id = vaga.id;
            }
            renderizarVagas(app, recrutador, candidatoService, empresaService, recrutadorService, vagaService, authService);
        }
    }));
}

function renderizarCandidatos(
    recrutador: Recrutador,
    candidatoService: CandidatoService,
    recrutadorService: RecrutadorService
): void {
    const painel = document.getElementById('painel-recrutador') as HTMLElement;
    painel.innerHTML = `<h3>Candidatos anônimos</h3><div class="row g-3">${candidatoService.listarCandidatos().map(candidato => `
      <div class="col-md-6"><div class="card h-100"><div class="card-body">
        <h5>${candidato.formacao}</h5><p>${candidato.descricao}</p>
        <small>${candidato.estado} · ${candidato.competencias.join(', ')}</small>
        <button class="btn btn-sm btn-primary mt-3 btn-curtir" data-cpf="${candidato.cpf}">Curtir em nome da empresa</button>
      </div></div></div>`).join('')}</div>`;
    painel.querySelectorAll('.btn-curtir').forEach(button => button.addEventListener('click', () => {
        const candidato = candidatoService.listarCandidatos().find(item => item.cpf === (button as HTMLElement).dataset.cpf);
        if (candidato) {
            recrutadorService.curtir(recrutador.cpf, candidato);
            (button as HTMLButtonElement).disabled = true;
            button.textContent = 'Curtido';
        }
    }));
}

function renderizarMatches(recrutador: Recrutador, recrutadorService: RecrutadorService): void {
    const painel = document.getElementById('painel-recrutador') as HTMLElement;
    const matches = recrutadorService.listarMatches(recrutador.empresa.cnpj);
    painel.innerHTML = `<h3>Matches da empresa</h3>${matches.length ? matches.map(match => `
      <div class="alert alert-success"><strong>${match.empresa.nome}</strong> encontrou compatibilidade com um perfil de ${match.candidato.formacao}.<br><small>Responsáveis: ${match.recrutadores.join(', ')}</small></div>
    `).join('') : '<p class="text-muted">Nenhum match registrado nesta sessão.</p>'}`;
}
