import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { VagaService } from '../services/VagaService';
import { criarListaVagas } from './listarVagas';
import { criarTelaInicial } from './home';
import { AuthService } from '../services/AuthService';
import { RecrutadorService } from '../services/RecrutadorService';

export function criarAreaCandidato(app: HTMLElement,
  candidatoService: CandidatoService,
  empresaService: EmpresaService,
  vagaService: VagaService,
  authService?: AuthService,
  recrutadorService?: RecrutadorService) {
  app.innerHTML = `
    <div class="container mt-5 text-center">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">Voltar</button>
      <h2>Área do Candidato</h2>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-vagas" class="btn btn-primary btn-lg">Ver Vagas</button>
      </div>
    </div>
  `;

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    authService?.logout();
    criarTelaInicial(app, candidatoService, empresaService, vagaService, authService, recrutadorService);
  });

  const btnVagas = document.getElementById('btn-vagas') as HTMLButtonElement;

  btnVagas.addEventListener('click', () => {
    criarListaVagas(app, vagaService, candidatoService, empresaService);
  });

}