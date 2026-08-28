import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { VagaService } from '../services/VagaService';
import { criarAreaCandidato } from './areaCandidato';
import { criarAreaEmpresa } from './areaEmpresa';

export function criarTelaInicial(
    app: HTMLElement,
    candidatoService: CandidatoService,
    empresaService: EmpresaService,
    vagaService: VagaService
) {
    app.innerHTML = `
    <div class="container mt-5 text-center">
      <h1>Linketinder</h1>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button id="btn-candidato" class="btn btn-primary btn-lg">Sou Candidato</button>
        <button id="btn-empresa" class="btn btn-success btn-lg">Sou Empresa</button>
      </div>
    </div>
  `;

    const btnCandidato = document.getElementById('btn-candidato') as HTMLButtonElement;
    const btnEmpresa = document.getElementById('btn-empresa') as HTMLButtonElement;

    btnCandidato.addEventListener('click', () => {
        criarAreaCandidato(app, candidatoService, empresaService, vagaService);
    });

    btnEmpresa.addEventListener('click', () => {
        criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
    });
}