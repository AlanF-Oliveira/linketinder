import { Candidato } from '../models/Candidato';
import { Empresa } from '../models/Empresa';
import { Recrutador } from '../models/Recrutador';

export interface MatchLocal {
    candidato: Candidato;
    empresa: Empresa;
    recrutadores: string[];
}

export class RecrutadorService {
    private recrutadores: Recrutador[];
    private curtidas = new Map<string, Set<string>>();
    private matches: MatchLocal[] = [];

    constructor(recrutadores: Recrutador[]) {
        this.recrutadores = recrutadores;
    }

    listarRecrutadores(): Recrutador[] {
        return this.recrutadores;
    }

    listarPorEmpresa(cnpj: string): Recrutador[] {
        return this.recrutadores.filter(recrutador => recrutador.empresa.cnpj === cnpj);
    }

    salvarRecrutador(recrutador: Recrutador): Recrutador[] {
        if (this.recrutadores.some(item => item.cpf === recrutador.cpf)) {
            throw new Error(`Recrutador com o CPF ${recrutador.cpf} já está cadastrado.`);
        }
        this.recrutadores.push(recrutador);
        return this.recrutadores;
    }

    atualizarRecrutador(cpf: string, dados: Recrutador): Recrutador[] {
        const recrutador = this.recrutadores.find(item => item.cpf === cpf);
        if (!recrutador) {
            throw new Error(`Recrutador com o CPF ${cpf} não encontrado.`);
        }
        if (recrutador.empresa.cnpj !== dados.empresa.cnpj) {
            throw new Error('O recrutador deve permanecer vinculado à empresa atual.');
        }
        Object.assign(recrutador, dados);
        return this.recrutadores;
    }

    removerRecrutador(cpf: string, cnpj: string): Recrutador[] {
        const index = this.recrutadores.findIndex(item =>
            item.cpf === cpf && item.empresa.cnpj === cnpj
        );
        if (index === -1) {
            throw new Error('Recrutador não encontrado para esta empresa.');
        }
        this.recrutadores.splice(index, 1);
        return this.recrutadores;
    }

    curtir(cpfRecrutador: string, candidato: Candidato): void {
        const recrutador = this.recrutadores.find(item => item.cpf === cpfRecrutador);
        if (!recrutador || recrutador.status !== 'ATIVO') {
            throw new Error('Somente recrutadores com vínculo ativo podem curtir candidatos.');
        }
        const curtidasEmpresa = this.curtidas.get(recrutador.empresa.cnpj) ?? new Set<string>();
        curtidasEmpresa.add(candidato.cpf);
        this.curtidas.set(recrutador.empresa.cnpj, curtidasEmpresa);

        const match = this.matches.find(item =>
            item.candidato.cpf === candidato.cpf &&
            item.empresa.cnpj === recrutador.empresa.cnpj
        );
        if (!match) {
            this.matches.push({
                candidato,
                empresa: recrutador.empresa,
                recrutadores: [recrutador.nome]
            });
        } else if (!match.recrutadores.includes(recrutador.nome)) {
            match.recrutadores.push(recrutador.nome);
        }
    }

    listarMatches(cnpj: string): MatchLocal[] {
        return this.matches.filter(match => match.empresa.cnpj === cnpj);
    }
}
