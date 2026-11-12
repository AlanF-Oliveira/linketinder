import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { VagaService } from '../services/VagaService';
import { RecrutadorService } from '../services/RecrutadorService';
import { AuthService } from '../services/AuthService';
import { criarLogin } from './login';
import { criarCadastroCandidato } from './cadastrarCandidato';
import { criarCadastroEmpresa } from './cadastrarEmpresa';

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
        <button id="btn-login" class="btn btn-primary btn-lg">Entrar</button>
        <button id="btn-cadastro-candidato" class="btn btn-outline-primary btn-lg">Cadastrar candidato</button>
        <button id="btn-cadastro-empresa" class="btn btn-outline-success btn-lg">Cadastrar empresa</button>
      </div>
    </div>
  `;

  document.getElementById('btn-login')?.addEventListener('click', () => {
    if (authService && recrutadorService) {
      criarLogin(app, authService, candidatoService, empresaService, recrutadorService, vagaService);
    }
  });

  document.getElementById('btn-cadastro-candidato')?.addEventListener('click', () => {
    criarCadastroCandidato(
      app, candidatoService, empresaService, vagaService, authService, recrutadorService
    );
  });

  document.getElementById('btn-cadastro-empresa')?.addEventListener('click', () => {
    criarCadastroEmpresa(
      app, empresaService, candidatoService, vagaService, authService, recrutadorService
    );
  });
}