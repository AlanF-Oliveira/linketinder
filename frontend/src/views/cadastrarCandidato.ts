import { Candidato } from "../models/Candidato";
import { CandidatoService } from "../services/CandidatoService";
import { EmpresaService } from "../services/EmpresaService";
import { VagaService } from "../services/VagaService";
import { criarAreaCandidato } from "./areaCandidato";

export function criarCadastroCandidato(app: HTMLElement, candidatoService: CandidatoService, empresaService: EmpresaService, vagaService: VagaService) {
  app.innerHTML = `
    <div class="container mt-4">
        <button id="btn-voltar" class="btn btn-outline-secondary mb-3">← Voltar</button>
        <h2>Cadastro de Candidato</h2>
        <form id="form-candidato">
        <div class="mb-3"><label class="form-label">Nome</label><input type="text" class="form-control" id="nome" name="nome" required></div>
        <div class="mb-3"><label class="form-label">Idade</label><input type="number" class="form-control" id="idade" name="idade" required></div>
        <div class="mb-3"><label class="form-label">Email</label><input type="email" class="form-control" id="email" name="email" required></div>
        <div class="mb-3"><label class="form-label">Formação</label><input type="text" class="form-control" id="formacao" name="formacao" required></div>
        <div class="mb-3"><label class="form-label">CPF</label><input type="text" class="form-control" id="cpf" name="cpf" required></div>
        <div class="mb-3"><label class="form-label">CEP</label><input type="text" class="form-control" id="cep" name="cep" required></div>
        <div class="mb-3"><label class="form-label">Estado</label><input type="text" class="form-control" id="estado" name="estado" required></div>
        <div class="mb-3"><label class="form-label">Descrição</label><textarea class="form-control" id="descricao" name="descricao" rows="3" required></textarea></div>
        <div class="mb-3"><label class="form-label">Competências (separadas por vírgula)</label><input type="text" class="form-control" id="competencias" name="competencias" placeholder="Java, Spring Boot, Docker"></div>
        <button type="submit" class="btn btn-primary">Cadastrar</button>
      </form>
    </div>
  `;

  const btnVoltar = document.getElementById('btn-voltar') as HTMLButtonElement;
  btnVoltar.addEventListener('click', () => {
    criarAreaCandidato(app, candidatoService, empresaService, vagaService);
  });

  const form = document.getElementById('form-candidato') as HTMLFormElement;
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(form);

    const nome = formData.get('nome') as string;
    const email = formData.get('email') as string;
    const cep = formData.get('cep') as string;
    const estado = formData.get('estado') as string;
    const descricao = formData.get('descricao') as string;
    const cpf = formData.get('cpf') as string;
    const idade = Number(formData.get('idade'));
    const formacao = formData.get('formacao') as string
    const competencias = (formData.get('competencias') as string).split(',').map(c => c.trim());

    const candidato = new Candidato(nome, email, cep, estado, descricao, cpf, idade, formacao, competencias);
    candidatoService.salvarCandidato(candidato);
    criarAreaCandidato(app, candidatoService, empresaService, vagaService);
  });
}