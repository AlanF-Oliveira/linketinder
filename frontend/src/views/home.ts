import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { VagaService } from '../services/VagaService';
import { criarAreaCandidato } from './areaCandidato';
import { criarAreaEmpresa } from './areaEmpresa';
import { RecrutadorService } from '../services/RecrutadorService';
import { AuthService } from '../services/AuthService';
import { criarLogin } from './login';

export function criarTelaInicial(
  app: HTMLElement,
  candidatoService: CandidatoService,
  empresaService: EmpresaService,
  vagaService: VagaService,
  authService?: AuthService,
  recrutadorService?: RecrutadorService
) {
  app.innerHTML = `
    <div class="container mt-5 text-center">
      <h1>Linketinder</h1>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-candidato" class="btn btn-primary btn-lg">Login candidato</button>
        <button id="btn-empresa" class="btn btn-success btn-lg">Login empresa</button>
        <button id="btn-recrutador" class="btn btn-dark btn-lg">Login recrutador</button>
      </div>
    </div>
  `;

  const btnCandidato = document.getElementById('btn-candidato') as HTMLButtonElement;
  const btnEmpresa = document.getElementById('btn-empresa') as HTMLButtonElement;

  btnCandidato.addEventListener('click', () => {
    if (authService && recrutadorService) {
      criarLogin(app, 'candidato', authService, candidatoService, empresaService, recrutadorService, vagaService);
    } else {
      criarAreaCandidato(app, candidatoService, empresaService, vagaService);
    }
  });

  btnEmpresa.addEventListener('click', () => {
    if (authService && recrutadorService) {
      criarLogin(app, 'empresa', authService, candidatoService, empresaService, recrutadorService, vagaService);
    } else {
      criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
    }
  });

  document.getElementById('btn-recrutador')?.addEventListener('click', () => {
    if (authService && recrutadorService) {
      criarLogin(app, 'recrutador', authService, candidatoService, empresaService, recrutadorService, vagaService);
    }
  });
}