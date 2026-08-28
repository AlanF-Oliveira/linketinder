import { Vaga } from "../models/Vaga";
import { VagaService } from "../services/VagaService";
import { EmpresaService } from "../services/EmpresaService";
import { CandidatoService } from "../services/CandidatoService";
import { criarAreaEmpresa } from "./areaEmpresa";

export function criarCadastroVaga(app: HTMLElement, vagaService: VagaService, empresaService: EmpresaService, candidatoService: CandidatoService) {
    const empresas = empresaService.listarEmpresas();

    const opcoesEmpresas = empresas
        .map(empresa => `<option value="${empresa.cnpj}">${empresa.nome} (${empresa.cnpj})</option>`)
        .join('');

    app.innerHTML = `
    <div class="container mt-4">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">Voltar</button>
      <h2>Cadastro de Vaga</h2>
      <form id="form-vaga">
        <div class="mb-3">
          <label class="form-label">Empresa</label>
          <select class="form-select" id="empresaCnpj" name="empresaCnpj" required>
            ${opcoesEmpresas}
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Título</label>
          <input type="text" class="form-control" id="titulo" name="titulo" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Descrição</label>
          <textarea class="form-control" id="descricao" name="descricao" rows="3" required></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Competências exigidas (separadas por vírgula)</label>
          <input type="text" class="form-control" id="competenciasExigidas" name="competenciasExigidas" placeholder="Java, Spring Boot, Docker">
        </div>
        <button type="submit" class="btn btn-primary">Cadastrar Vaga</button>
      </form>
    </div>
  `;

    const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
    btnVoltar.addEventListener('click', () => {
        criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
    });

    const form = document.getElementById('form-vaga') as HTMLFormElement;
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData(form);

        const empresaCnpj = formData.get('empresaCnpj') as string;
        const titulo = formData.get('titulo') as string;
        const descricao = formData.get('descricao') as string;
        const competenciasExigidas = (formData.get('competenciasExigidas') as string).split(',').map(c => c.trim());

        const empresa = empresas.find(e => e.cnpj === empresaCnpj);
        if (!empresa) {
            throw new Error(`Empresa com CNPJ ${empresaCnpj} não encontrada.`);
        }

        const vaga = new Vaga(titulo, descricao, competenciasExigidas, empresa);
        vagaService.criarVaga(vaga);
        criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
    });
}