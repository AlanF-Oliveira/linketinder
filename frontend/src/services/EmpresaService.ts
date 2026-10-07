import { Empresa } from '../models/Empresa';

export class EmpresaService {
    private empresas: Empresa[];

    constructor(empresas: Empresa[]) {
        this.empresas = empresas;
    }

    salvarEmpresa(empresa: Empresa): Empresa[] {
        const cnpjJaCadastrado = this.empresas.some(
            empresaCadastrada => empresaCadastrada.cnpj === empresa.cnpj
        );

        if (cnpjJaCadastrado) {
            throw new Error(
                `Empresa com o CNPJ ${empresa.cnpj} já está cadastrada.`
            );
        }

        this.empresas.push(empresa);
        return this.empresas;
    }

    listarEmpresas(): Empresa[] {
        return this.empresas;
    }

    atualizarEmpresa(
        cnpj: string,
        empresa: Empresa
    ): Empresa[] {
        const empresaEncontrada = this.empresas.find(
            empresaCadastrada => empresaCadastrada.cnpj === cnpj
        );

        if (!empresaEncontrada) {
            throw new Error(
                `Empresa com o CNPJ ${cnpj} não encontrada.`
            );
        }

        Object.assign(empresaEncontrada, empresa);
        return this.empresas;
    }

    deletarEmpresa(cnpj: string): Empresa[] {
        const indiceEmpresa = this.empresas.findIndex(
            empresa => empresa.cnpj === cnpj
        );

        if (indiceEmpresa === -1) {
            throw new Error(
                `Empresa com o CNPJ ${cnpj} não encontrada.`
            );
        }

        this.empresas.splice(indiceEmpresa, 1);
        return this.empresas;
    }
}