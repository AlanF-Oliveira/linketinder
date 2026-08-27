import { EmpresaService } from '../services/EmpresaService';
import { CandidatoService } from '../services/CandidatoService';
import { criarCadastroEmpresa } from './cadastrarEmpresa';

export function criarAreaEmpresa(app: HTMLElement, empresaService: EmpresaService, candidatoService: CandidatoService) {
    app.innerHTML = `
    <div class="container mt-5 text-center">
      <h2>Área da Empresa</h2>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-candidatos" class="btn btn-primary btn-lg">Ver candidatos</button>
        <button id="btn-cadastro" class="btn btn-secondary btn-lg">Cadastro</button>
      </div>
    </div>
  `;

    const btnCandidatos = document.getElementById('btn-candidatos') as HTMLButtonElement;
    const btnCadastro = document.getElementById('btn-cadastro') as HTMLButtonElement;

    btnCandidatos.addEventListener('click', () => {
        alert('...');
    });

    btnCadastro.addEventListener('click', () => {
        criarCadastroEmpresa(app, empresaService);
    });
}