export abstract class Usuario {
    nome: string;
    email: string;
    cep: string;
    estado: string;
    descricao: string;

    constructor(nome: string, email: string, cep: string, estado: string, descricao: string) {
        this.nome = nome;
        this.email = email;
        this.cep = cep;
        this.estado = estado;
        this.descricao = descricao;
    }
}