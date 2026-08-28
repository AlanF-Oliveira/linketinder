import { Usuario } from "./Usuario";

export class Candidato extends Usuario {

    cpf: string;
    idade: number;
    formacao: string;
    competencias: string[];

    constructor(nome: string, email: string, cep: string, estado: string, descricao: string, cpf: string, idade: number,formacao: string, competencias: string[] = []) {
        super(nome, email, cep, estado, descricao)
        this.cpf = cpf;
        this.idade = idade;
        this.formacao = formacao
        this.competencias = competencias;
    }

    toString(): string {
        return `${this.nome} | ${this.idade} | ${this.email} | ${this.cpf} | ${this.descricao} | ${this.estado} | ${this.cep} |${this.formacao} | ${this.competencias}`;
    }
}

export type CandidatoAnonimo = Omit<Candidato, 'nome' | 'email' | 'cpf' | 'cep'>;

export function anonimizarCandidato(candidato: Candidato): CandidatoAnonimo {
    const { nome, email, cpf, cep, ...resto } = candidato;
    return resto;
}