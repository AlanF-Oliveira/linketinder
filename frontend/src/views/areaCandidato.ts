import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { VagaService } from '../services/VagaService';
import { criarCadastroCandidato } from './cadastrarCandidato';
import { criarListaVagas } from './listarVagas';
import { criarTelaInicial } from './home';

export function criarAreaCandidato(app: HTMLElement, candidatoService: CandidatoService, empresaService: EmpresaService, vagaService: VagaService) {
  app.innerHTML = `
    <div class="container mt-5 text-center">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">Voltar</button>
      <h2>Área do Candidato</h2>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-vagas" class="btn btn-primary btn-lg">Ver Vagas</button>
        <button id="btn-cadastro" class="btn btn-secondary btn-lg">Cadastro</button>
      </div>
    </div>
  `;

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    criarTelaInicial(app, candidatoService, empresaService, vagaService);
  });

  const btnVagas = document.getElementById('btn-vagas') as HTMLButtonElement;
  const btnCadastro = document.getElementById('btn-cadastro') as HTMLButtonElement;

  btnVagas.addEventListener('click', () => {
    criarListaVagas(app, vagaService, candidatoService, empresaService);
  });

  btnCadastro.addEventListener('click', () => {
    criarCadastroCandidato(app, candidatoService, empresaService, vagaService);
  });
}