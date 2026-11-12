import { Empresa } from './Empresa';

export type StatusVinculo = 'ATIVO' | 'INATIVO';

export interface EventoVinculo {
    statusAnterior: StatusVinculo;
    novoStatus: StatusVinculo;
    data: string;
    responsavel: string;
}

export class Recrutador {
    nome: string;
    areaAtuacao: string;
    descricao: string;
    email: string;
    cpf: string;
    dataNascimento: string;
    senha: string;
    empresa: Empresa;
    status: StatusVinculo;
    historico: EventoVinculo[];

    constructor(
        nome: string,
        areaAtuacao: string,
        descricao: string,
        email: string,
        cpf: string,
        dataNascimento: string,
        senha: string,
        empresa: Empresa,
        status: StatusVinculo = 'ATIVO',
        historico: EventoVinculo[] = []
    ) {
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
        this.descricao = descricao;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        this.empresa = empresa;
        this.status = status;
        this.historico = historico;
    }

    alterarStatus(novoStatus: StatusVinculo, responsavel: string): void {
        if (this.status === novoStatus) {
            return;
        }

        this.historico.push({
            statusAnterior: this.status,
            novoStatus,
            data: new Date().toISOString(),
            responsavel
        });
        this.status = novoStatus;
    }
}
