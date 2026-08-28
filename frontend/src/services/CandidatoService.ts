import { Candidato } from '../models/Candidato';

export class CandidatoService {
    private lista: Candidato[];

    constructor(lista: Candidato[]) {
        this.lista = lista;
    }

    salvarCandidato(candidato: Candidato): Candidato[] {
        const jaExiste = this.lista.some(cand => cand.cpf === candidato.cpf);
        if (jaExiste) {
            throw new Error(`Candidato com o CPF ${candidato.cpf} já está cadastrado.`);
        }

        this.lista.push(candidato);
        console.log(this.lista)
        return this.lista;
    }

    listarCandidatos(): Candidato[] {
        return this.lista;
    }

    atualizarCandidato(cpf: string, candidato: Candidato): Candidato[] {
        const candidatoAtualizado = this.lista.find(cand => cand.cpf === cpf);
        if (!candidatoAtualizado) {
            throw new Error(`Candidato com o CPF ${cpf} não encontrado `)
        }

        Object.assign(candidatoAtualizado, candidato);
        return this.lista
    }

    deletarCandidato(cpf: string): Candidato[] {
        const index = this.lista.findIndex(candidato => candidato.cpf === cpf);
        if (index === -1) {
            throw new Error(`Candidato com o CPF ${cpf} não encontrado `)
        }

        this.lista.splice(index, 1);
        return this.lista;
    }


}