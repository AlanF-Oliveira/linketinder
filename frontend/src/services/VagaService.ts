import { Vaga } from "../models/Vaga";

export class VagaService {

    private lista: Vaga[];

    constructor(lista: Vaga[]) {
        this.lista = lista;
    }

    criarVaga(vaga: Vaga): Vaga[] {
        this.lista.push(vaga)
        return this.lista;
    }

    listarVagas(): Vaga[] {
        return this.lista;
    }

    alterarVaga(vaga: Vaga, titulo: string): Vaga[] {
        const vagaAtualizada = this.lista.find(vagaAntiga => vagaAntiga.titulo === titulo);
        if (!vagaAtualizada) {
            throw new Error(`Vaga não encontrada ${titulo}.`);
        }
        vagaAtualizada.titulo = vaga.titulo
        vagaAtualizada.descricao = vaga.descricao
        vagaAtualizada.competenciasExigidas = vaga.competenciasExigidas
        return this.lista
    }

    deletarVaga(titulo: string): Vaga[] {
        const index = this.lista.findIndex(vaga => vaga.titulo === titulo);
        if (index === -1) {
            throw new Error(`Vaga não encontrada ${titulo}`);
        }

        this.lista.splice(index, 1);
        return this.lista;
    }


}