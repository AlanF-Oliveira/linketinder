import { Empresa } from "../models/Empresa";
import { EmpresaService } from "../services/EmpresaService";
import { CandidatoService } from "../services/CandidatoService";
import { criarAreaEmpresa } from "./areaEmpresa";
import type { VagaService } from "../services/VagaService";

export function criarCadastroEmpresa(app: HTMLElement, empresaService: EmpresaService, candidatoService: CandidatoService, vagaService: VagaService) {
  app.innerHTML = `
    <div class="container mt-4">
      <button id="btn-voltar" class="btn btn-outline-secondary mb-3">← Voltar</button>
      <h2>Cadastro de Empresa</h2>
      <form id="form-empresa">
        <div class="mb-3"><label class="form-label">Nome</label><input type="text" class="form-control" id="nome" name="nome" required></div>
        <div class="mb-3"><label class="form-label">Email</label><input type="email" class="form-control" id="email" name="email" required></div>
        <div class="mb-3"><label class="form-label">CNPJ</label><input type="text" class="form-control" id="cnpj" name="cnpj" required></div>
        <div class="mb-3"><label class="form-label">País</label><input type="text" class="form-control" id="pais" name="pais" required></div>
        <div class="mb-3"><label class="form-label">Estado</label><input type="text" class="form-control" id="estado" name="estado" required></div>
        <div class="mb-3"><label class="form-label">CEP</label><input type="text" class="form-control" id="cep" name="cep" required></div>
        <div class="mb-3"><label class="form-label">Descrição</label><textarea class="form-control" id="descricao" name="descricao" rows="3" required></textarea></div>
        <div class="mb-3"><label class="form-label">Competências desejadas (separadas por vírgula)</label><input type="text" class="form-control" id="competenciasDesejadas" name="competenciasDesejadas" placeholder="Java, Docker, AWS"></div>
        <button type="submit" class="btn btn-primary">Cadastrar</button>
      </form>
    </div>
  `;

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
  });

  const form = document.getElementById('form-empresa') as HTMLFormElement;
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(form);

    const nome = formData.get('nome') as string;
    const email = formData.get('email') as string;
    const cnpj = formData.get('cnpj') as string;
    const pais = formData.get('pais') as string;
    const estado = formData.get('estado') as string;
    const cep = formData.get('cep') as string;
    const descricao = formData.get('descricao') as string;
    const competenciasDesejadas = (formData.get('competenciasDesejadas') as string).split(',').map(c => c.trim());

    const empresa = new Empresa(nome, email, cnpj, pais, estado, cep, descricao, competenciasDesejadas);
    empresaService.salvarEmpresa(empresa);
    criarAreaEmpresa(app, empresaService, candidatoService, vagaService);
  });
}