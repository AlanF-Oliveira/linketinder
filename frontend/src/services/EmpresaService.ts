import { Empresa } from "../models/Empresa";

export class EmpresaService {

    private lista: Empresa[];

    constructor(lista: Empresa[]) {
        this.lista = lista
    }

    salvarEmpresa(empresa: Empresa): Empresa[] {
        const jaExiste = this.lista.some(emp => emp.cnpj === empresa.cnpj);
        if (jaExiste) {
            throw new Error(`Empresa com o CNPJ ${empresa.cnpj} já está cadastrada.`);
        }
        this.lista.push(empresa)
        return this.lista
    }

    listarEmpresa(): Empresa[] {
        return this.lista
    }

    atualizarEmpresa(cnpj: string, empresa: Empresa): Empresa[] {
        const empresaAtualizada = this.lista.find(emp => emp.cnpj === cnpj);
        if (!empresaAtualizada) {
            throw new Error(`Empresa com o CNPJ ${cnpj} não encontrada `)
        }

        empresaAtualizada.nome = empresa.nome;
        empresaAtualizada.cnpj = empresa.cnpj;
        empresaAtualizada.email = empresa.email;
        empresaAtualizada.cep = empresa.cep;
        empresaAtualizada.estado = empresa.estado;
        empresaAtualizada.pais = empresa.pais;
        empresaAtualizada.descricao = empresa.descricao;
        empresaAtualizada.competenciasDesejadas = empresa.competenciasDesejadas
        return this.lista
    }

    deletarEmpresa(cnpj: string): Empresa[] {
        const index = this.lista.findIndex(empresa => empresa.cnpj === cnpj)
        if (index === -1) {
            throw new Error(`Empresa com o CNPJ ${cnpj} não encontrado `)
        }
        this.lista.splice(index, 1)
        return this.lista
    }




}