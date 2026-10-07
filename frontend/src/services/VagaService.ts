import { Vaga } from '../models/Vaga';

export class VagaService {
    private vagas: Vaga[];

    constructor(vagas: Vaga[]) {
        this.vagas = vagas;
    }

    criarVaga(vaga: Vaga): Vaga[] {
        const idJaCadastrado = this.vagas.some(
            vagaCadastrada => vagaCadastrada.id === vaga.id
        );

        if (idJaCadastrado) {
            throw new Error(
                `Vaga com ID ${vaga.id} já cadastrada.`
            );
        }

        this.vagas.push(vaga);
        return this.vagas;
    }

    listarVagas(): Vaga[] {
        return this.vagas;
    }

    alterarVaga(id: number, vaga: Vaga): Vaga[] {
        const vagaEncontrada = this.vagas.find(
            vagaCadastrada => vagaCadastrada.id === id
        );

        if (!vagaEncontrada) {
            throw new Error(
                `Vaga com ID ${id} não encontrada.`
            );
        }

        Object.assign(vagaEncontrada, vaga);
        return this.vagas;
    }

    deletarVaga(id: number): Vaga[] {
        const indiceVaga = this.vagas.findIndex(
            vaga => vaga.id === id
        );

        if (indiceVaga === -1) {
            throw new Error(
                `Vaga com ID ${id} não encontrada.`
            );
        }

        this.vagas.splice(indiceVaga, 1);
        return this.vagas;
    }
}