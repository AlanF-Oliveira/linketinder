import { Candidato } from "../models/Candidato";
import { CandidatoService } from "../services/CandidatoService";
export function criarCadastroCandidato(app: HTMLElement, candidatoService: CandidatoService) {
    app.innerHTML = `
    <div class="container mt-4">
        <h2>Cadastro de Candidato</h2>
        <form id="form-candidato">
        <div class="mb-3">
          <label class="form-label">Nome</label>
          <input type="text" class="form-control" id="nome" name="nome" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Idade</label>
          <input type="number" class="form-control" id="idade" name="idade" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
          <label class="form-label">CPF</label>
          <input type="text" class="form-control" id="cpf" name="cpf" required>
        </div>
        <div class="mb-3">
          <label class="form-label">CEP</label>
          <input type="text" class="form-control" id="cep" name="cep" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Estado</label>
          <input type="text" class="form-control" id="estado" name="estado" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Descrição</label>
          <textarea class="form-control" id="descricao" name="descricao" rows="3" required></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Competências (separadas por vírgula)</label>
          <input type="text" class="form-control" id="competencias" name="competencias" placeholder="Java,Typescript,Docker">
        </div>
        <button type="submit" class="btn btn-primary">Cadastrar</button>
      </form>
    </div>
  `;

    const form = document.getElementById('form-candidato') as HTMLFormElement;

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData(form)
        const nome = formData.get('nome') as string;
        const email = formData.get('email') as string;
        const cep = formData.get('cep') as string;
        const estado = formData.get('estado') as string;
        const descricao = formData.get('descricao') as string;
        const cpf = formData.get('cpf') as string;
        const idade = Number(formData.get('idade'));
        const competencias = (formData.get('competencias') as string).split(',');
        const candidato = new Candidato(nome, email, cep, estado, descricao, cpf, idade, competencias);
        candidatoService.salvarCandidato(candidato)
    })
}