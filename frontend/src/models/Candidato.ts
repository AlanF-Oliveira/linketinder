import { Usuario } from "./Usuario";

export class Candidato extends Usuario {

    cpf: string;
    idade: number;
    competencias: string[];

    constructor(nome: string, email: string, cep: string, estado: string, descricao: string, cpf: string, idade: number, competencias: string[] = []) {
        super(nome, email, cep, estado, descricao)
        this.cpf = cpf;
        this.idade = idade;
        this.competencias = competencias;
    }

    toString(): string {
        return `${this.nome} | ${this.idade} | ${this.email} | ${this.cpf} | ${this.descricao} | ${this.estado} | ${this.cep} | ${this.competencias}`;
    }
}