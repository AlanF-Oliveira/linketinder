import { CandidatoService } from '../services/CandidatoService';
import { VagaService } from '../services/VagaService';
import { criarCadastroCandidato } from './cadastrarCandidato';

export function criarAreaCandidato(app: HTMLElement, candidatoService: CandidatoService, vagaService: VagaService) {
    app.innerHTML = `
    <div class="container mt-5 text-center">
      <h2>Área do Candidato</h2>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-vagas" class="btn btn-primary btn-lg">Ver Vagas</button>
        <button id="btn-cadastro" class="btn btn-secondary btn-lg">Cadastro</button>
      </div>
    </div>
  `;

    const btnPerfil = document.getElementById('btn-vagas') as HTMLButtonElement;
    const btnCadastro = document.getElementById('btn-cadastro') as HTMLButtonElement;

    btnPerfil.addEventListener('click', () => {
        alert('Pagina criada')
    });

    btnCadastro.addEventListener('click', () => {
        criarCadastroCandidato(app, candidatoService);
    });
}