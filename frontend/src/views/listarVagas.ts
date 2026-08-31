import { VagaService } from '../services/VagaService';
import { CandidatoService } from '../services/CandidatoService';
import { anonimizarEmpresa } from '../models/Empresa';
import { criarAreaCandidato } from './areaCandidato';
import type { EmpresaService } from '../services/EmpresaService';

export function criarListaVagas(app: HTMLElement, vagaService: VagaService, candidatoService: CandidatoService, empresaService: EmpresaService) {
  const vagas = vagaService.listarVagas();

  const cardsHtml = vagas
    .map(vaga => {
      const empresaAnonima = anonimizarEmpresa(vaga.empresa);
      const infoTooltip = `Local: ${empresaAnonima.estado}, ${empresaAnonima.pais} | Competências: ${vaga.competenciasExigidas.join(', ')}`;
      return `
        <div class="card mb-3" title="${infoTooltip}">
          <div class="card-body">
            <h5 class="card-title">${vaga.titulo}</h5>
            <p class="card-text">${vaga.descricao}</p>
            <p class="card-text"><small class="text-muted">Competências: ${vaga.competenciasExigidas.join(', ')}</small></p>
            <p class="card-text"><small class="text-muted">Empresa em: ${empresaAnonima.estado}, ${empresaAnonima.pais}</small></p>
            <button class="btn btn-danger btn-sm btn-deletar-vaga" data-id="${vaga.id}">Excluir</button>
          </div>
        </div>
      `;
    })
    .join('');

  app.innerHTML = `
    <div class="container mt-4">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">Voltar</button>
      <h2>Vagas Disponíveis</h2>
      ${cardsHtml}
    </div>
  `;

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    criarAreaCandidato(app, candidatoService, empresaService, vagaService);
  });

  const botoesDeletar = document.querySelectorAll('.btn-deletar-vaga');
  botoesDeletar.forEach(botao => {
    botao.addEventListener('click', () => {
      const idString = botao.getAttribute('data-id');
      try {
        if (idString) {
          vagaService.deletarVaga(Number(idString));
          criarListaVagas(app, vagaService, candidatoService, empresaService);
        }
      } catch (error) {
        alert(error instanceof Error ? error.message : 'Erro ao deletar vaga')
      }
    });
  });
}