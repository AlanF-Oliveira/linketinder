import { EmpresaService } from '../services/EmpresaService';
import { CandidatoService } from '../services/CandidatoService';
import { VagaService } from '../services/VagaService';
import { criarListaCandidatos } from './listarCandidatos';
import { criarTelaInicial } from './home';
import { criarCadastroVaga } from './cadastrarVaga';
import { RecrutadorService } from '../services/RecrutadorService';
import { AuthService } from '../services/AuthService';
import { criarGestaoRecrutadores } from './gestaoRecrutadores';

export function criarAreaEmpresa(app: HTMLElement,
  empresaService: EmpresaService,
  candidatoService: CandidatoService,
  vagaService: VagaService,
  recrutadorService?: RecrutadorService,
  authService?: AuthService) {
  app.innerHTML = `
    <div class="container mt-5 text-center">
      <button id="btn-sair" class="btn btn-outline-secondary mb-3">Sair</button>
      <h2>Área da Empresa</h2>
      <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
        <button id="btn-candidatos" class="btn btn-primary btn-lg">Ver candidatos</button>
        <button id="btn-cadastro-vaga" class="btn btn-secondary btn-lg">Cadastrar Vaga</button>
        <button id="btn-recrutadores" class="btn btn-dark btn-lg">Gerenciar recrutadores</button>
      </div>
    </div>
  `;

  const btnSair = document.getElementById('btn-sair') as HTMLButtonElement;
  btnSair.addEventListener('click', () => {
    authService?.logout();
    criarTelaInicial(app, candidatoService, empresaService, vagaService, authService, recrutadorService);
  });

  const btnCandidatos = document.getElementById('btn-candidatos') as HTMLButtonElement;
  const btnCadastrarVaga = document.getElementById('btn-cadastro-vaga') as HTMLButtonElement;

  btnCandidatos.addEventListener('click', () => {
    criarListaCandidatos(app, candidatoService, empresaService, vagaService, recrutadorService, authService);
  });

  btnCadastrarVaga.addEventListener('click', () =>
    criarCadastroVaga(app, vagaService, empresaService, candidatoService, recrutadorService, authService)
  );

  if (recrutadorService && authService) {
    document.getElementById('btn-recrutadores')?.addEventListener('click', () =>
      criarGestaoRecrutadores(app, empresaService, candidatoService, vagaService, recrutadorService, authService)
    );
  }
}