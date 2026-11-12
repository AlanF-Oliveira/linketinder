import { Candidato } from '../models/Candidato';
import { Empresa } from '../models/Empresa';
import { Recrutador } from '../models/Recrutador';

export type TipoUsuario = 'candidato' | 'empresa' | 'recrutador';
export type SessaoLocal =
    | { tipo: 'candidato'; usuario: Candidato }
    | { tipo: 'empresa'; usuario: Empresa }
    | { tipo: 'recrutador'; usuario: Recrutador };

export class AuthService {
    private sessao: SessaoLocal | null = null;
    private candidatos: Candidato[];
    private empresas: Empresa[];
    private recrutadores: Recrutador[];

    constructor(
        candidatos: Candidato[],
        empresas: Empresa[],
        recrutadores: Recrutador[]
    ) {
        this.candidatos = candidatos;
        this.empresas = empresas;
        this.recrutadores = recrutadores;
    }

    login(identificador: string, senha: string): SessaoLocal {
        const identificadorNormalizado = identificador.trim();
        const candidatoCadastrado = this.candidatos.find(item =>
            item.cpf.trim() === identificadorNormalizado
        );
        const recrutadorCadastrado = this.recrutadores.find(item =>
            item.cpf.trim() === identificadorNormalizado
        );
        if (candidatoCadastrado && recrutadorCadastrado) {
            throw new Error('CPF cadastrado para mais de um tipo de usuário.');
        }

        const candidato = candidatoCadastrado?.senha === senha ? candidatoCadastrado : undefined;
        const recrutador = recrutadorCadastrado?.senha === senha ? recrutadorCadastrado : undefined;
        const empresa = this.empresas.find(item =>
            item.cnpj.trim() === identificadorNormalizado && item.senha === senha
        );

        if (recrutador && recrutador.status !== 'ATIVO') {
            throw new Error('O recrutador não possui vínculo ativo.');
        }
        if (candidato) {
            this.sessao = { tipo: 'candidato', usuario: candidato };
        } else if (recrutador) {
            this.sessao = { tipo: 'recrutador', usuario: recrutador };
        } else if (empresa) {
            this.sessao = { tipo: 'empresa', usuario: empresa };
        } else {
            throw new Error('Credenciais inválidas.');
        }
        return this.sessao;
    }

    obterSessao(): SessaoLocal | null {
        return this.sessao;
    }

    logout(): void {
        this.sessao = null;
    }
}
