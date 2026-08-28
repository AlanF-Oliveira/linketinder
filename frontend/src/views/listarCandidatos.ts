import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { anonimizarCandidato } from '../models/Candidato';
import { criarGraficoCompetencias } from './graficoCompetencias';
import { criarAreaEmpresa } from './areaEmpresa';
import { VagaService } from '../services/VagaService';

export function criarListaCandidatos(app: HTMLElement, candidatoService: CandidatoService, empresaService: EmpresaService, vagaService: VagaService) {
  const candidatos = candidatoService.listarCandidatos();

  const linhasHtml = candidatos
    .map(candidato => {
      const candidatoAnonimo = anonimizarCandidato(candidato);
      const infoTooltip = `Formação: ${candidatoAnonimo.formacao} | Idade: ${candidatoAnonimo.idade} | Estado: ${candidatoAnonimo.estado}`;
      return `
      <tr title="${infoTooltip}">
        <td>${candidatoAnonimo.formacao}</td>
        <td>${candidatoAnonimo.idade}</td>
        <td>${candidatoAnonimo.estado}</td>
        <td>${candidatoAnonimo.competencias.join(', ')}</td>
        <td>${candidatoAnonimo.descricao}</td>
        <td><button class="btn btn-danger btn-sm btn-deletar" data-cpf="${candidato.cpf}">Excluir</button></td>
      </tr>
    `;
    })
    .join('');

  app.innerHTML = `
    <div class="container mt-4">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">← Voltar</button>
      <h2>Candidatos Cadastrados</h2>
      <table class="table table-striped">
        <thead>
          <tr><th>Formação</th><th>Idade</th><th>Estado</th><th>Competências</th><th>Descrição</th><th>Ações</th></tr>
        </thead>
        <tbody>${linhasHtml}</tbody>
      </table>
      <h2 class="mt-5">Candidatos por Competência</h2>
      <canvas id="grafico-competencias"></canvas>
    </div>
  `;

  criarGraficoCompetencias('grafico-competencias', candidatos);

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
  });

  const botoesDeletar = document.querySelectorAll('.btn-deletar');
  botoesDeletar.forEach(botao => {
    botao.addEventListener('click', () => {
      const cpf = botao.getAttribute('data-cpf');
      if (cpf) {
        candidatoService.deletarCandidato(cpf);
        criarListaCandidatos(app, candidatoService, empresaService, vagaService);
      }
    });
  });
}