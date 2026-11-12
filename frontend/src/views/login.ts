import { AuthService } from '../services/AuthService';
import type { TipoUsuario } from '../services/AuthService';
import { CandidatoService } from '../services/CandidatoService';
import { EmpresaService } from '../services/EmpresaService';
import { RecrutadorService } from '../services/RecrutadorService';
import { VagaService } from '../services/VagaService';
import { criarTelaInicial } from './home';
import { criarAreaCandidato } from './areaCandidato';
import { criarAreaEmpresa } from './areaEmpresa';
import { criarAreaRecrutador } from './areaRecrutador';
import { criarCadastroCandidato } from './cadastrarCandidato';
import { criarCadastroEmpresa } from './cadastrarEmpresa';

export function criarLogin(
    app: HTMLElement,
    tipo: TipoUsuario,
    authService: AuthService,
    candidatoService: CandidatoService,
    empresaService: EmpresaService,
    recrutadorService: RecrutadorService,
    vagaService: VagaService
): void {
    const label = tipo === 'empresa' ? 'CNPJ' : 'CPF';
    app.innerHTML = `
      <div class="container mt-5" style="max-width: 520px">
        <button id="btn-voltar" class="btn btn-outline-secondary mb-4">Voltar</button>
        <h2>Login de ${tipo}</h2>
        <p class="text-muted">Acesso local do frontend, sem integração com o backend.</p>
        <form id="form-login">
          <div class="mb-3">
            <label class="form-label" for="identificador">${label}</label>
            <input id="identificador" name="identificador" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label" for="senha">Senha</label>
            <input id="senha" name="senha" type="password" class="form-control" required>
          </div>
          <div id="login-erro" class="alert alert-danger d-none" role="alert"></div>
          <button class="btn btn-primary w-100" type="submit">Entrar</button>
        </form>
        <div class="mt-4 text-center">
          <p class="text-muted mb-2">Ainda não possui cadastro?</p>
          ${tipo === 'candidato'
            ? '<button id="btn-cadastro" class="btn btn-outline-primary">Cadastrar candidato</button>'
            : tipo === 'empresa'
                ? '<button id="btn-cadastro" class="btn btn-outline-success">Cadastrar empresa</button>'
                : '<p class="small text-muted mb-0">O cadastro de recrutador é realizado pela empresa após o login.</p>'}
      </div>
    `;

    document.getElementById('btn-voltar')?.addEventListener('click', () =>
        criarTelaInicial(app, candidatoService, empresaService, vagaService, authService, recrutadorService)
    );

    document.getElementById('btn-cadastro')?.addEventListener('click', () => {
        if (tipo === 'candidato') {
            criarCadastroCandidato(app, candidatoService, empresaService, vagaService);
        } else if (tipo === 'empresa') {
            criarCadastroEmpresa(app, empresaService, candidatoService, vagaService);
        }
    });

    document.getElementById('form-login')?.addEventListener('submit', event => {
        event.preventDefault();
        const form = event.currentTarget as HTMLFormElement;
        const dados = new FormData(form);
        const identificador = String(dados.get('identificador')).trim();
        const senha = String(dados.get('senha'));
        const erro = document.getElementById('login-erro') as HTMLElement;

        try {
            const sessao = authService.login(identificador, senha);
            if (sessao.tipo !== tipo) {
                throw new Error('As credenciais pertencem a outro tipo de usuário.');
            }
            if (sessao.tipo === 'candidato') {
                criarAreaCandidato(app, candidatoService, empresaService, vagaService, authService, recrutadorService);
            } else if (sessao.tipo === 'empresa') {
                criarAreaEmpresa(app, empresaService, candidatoService, vagaService, recrutadorService, authService);
            } else {
                criarAreaRecrutador(app, sessao.usuario, candidatoService, empresaService, recrutadorService, vagaService, authService);
            }
        } catch (error) {
            erro.textContent = error instanceof Error ? error.message : 'Não foi possível entrar.';
            erro.classList.remove('d-none');
        }
    });
}
