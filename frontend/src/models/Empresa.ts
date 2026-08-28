import { Usuario } from './Usuario';

export class Empresa extends Usuario {
    cnpj: string;
    pais: string;
    competenciasDesejadas: string[];

    constructor(nome: string, email: string, cnpj: string, pais: string, estado: string, cep: string, descricao: string, competenciasDesejadas: string[] = []) {
        super(nome, email, cep, estado, descricao);
        this.cnpj = cnpj;
        this.pais = pais;
        this.competenciasDesejadas = competenciasDesejadas;
    }


    toString(): string {
        return `${this.nome}  | ${this.email} | ${this.cnpj} | ${this.pais} |${this.estado} | ${this.cep} | ${this.descricao} | ${this.competenciasDesejadas}`;
    }
}

export type EmpresaAnonima = Omit<Empresa, 'nome' | 'email' | 'cnpj' | 'cep'>;
export function anonimizarEmpresa(empresa: Empresa): EmpresaAnonima {
    const { nome, email, cnpj, cep, ...resto } = empresa;
    return resto;
}