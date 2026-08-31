import { Vaga } from "../models/Vaga";

export class VagaService {

    private lista: Vaga[];

    constructor(lista: Vaga[]) {
        this.lista = lista;
    }

    criarVaga(vaga: Vaga): Vaga[] {
        const jaExiste = this.lista.some(v => v.id === vaga.id)
        if (jaExiste) {
            throw new Error(`Vaga com ID ${vaga.id}já cadastrada `)
        }
        this.lista.push(vaga)
        return this.lista;
    }

    listarVagas(): Vaga[] {
        return this.lista;
    }

    alterarVaga(id: number, vaga: Vaga): Vaga[] {
        const vagaAtualizada = this.lista.find(vagaAntiga => vagaAntiga.id === id);
        if (!vagaAtualizada) {
            throw new Error(`Vaga com ID ${id} não encontrada`);
        }
        Object.assign(vagaAtualizada, vaga)
        return this.lista
    }

    deletarVaga(id: number): Vaga[] {
        const index = this.lista.findIndex(vaga => vaga.id === id);
        if (index === -1) {
            throw new Error(`Vaga com ID ${id} não encontrada`);
        }

        this.lista.splice(index, 1);
        return this.lista;
    }


}