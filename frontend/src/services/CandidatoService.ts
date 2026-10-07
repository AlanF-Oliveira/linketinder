import { Candidato } from '../models/Candidato';

export class CandidatoService {
    private candidatos: Candidato[];

    constructor(candidatos: Candidato[]) {
        this.candidatos = candidatos;
    }

    salvarCandidato(candidato: Candidato): Candidato[] {
        const cpfJaCadastrado = this.candidatos.some(
            candidatoCadastrado => candidatoCadastrado.cpf === candidato.cpf
        );

        if (cpfJaCadastrado) {
            throw new Error(
                `Candidato com o CPF ${candidato.cpf} já está cadastrado.`
            );
        }

        this.candidatos.push(candidato);
        return this.candidatos;
    }

    listarCandidatos(): Candidato[] {
        return this.candidatos;
    }

    atualizarCandidato(
        cpf: string,
        candidato: Candidato
    ): Candidato[] {
        const candidatoEncontrado = this.candidatos.find(
            candidatoCadastrado => candidatoCadastrado.cpf === cpf
        );

        if (!candidatoEncontrado) {
            throw new Error(
                `Candidato com o CPF ${cpf} não encontrado.`
            );
        }

        Object.assign(candidatoEncontrado, candidato);
        return this.candidatos;
    }

    deletarCandidato(cpf: string): Candidato[] {
        const indiceCandidato = this.candidatos.findIndex(
            candidato => candidato.cpf === cpf
        );

        if (indiceCandidato === -1) {
            throw new Error(
                `Candidato com o CPF ${cpf} não encontrado.`
            );
        }

        this.candidatos.splice(indiceCandidato, 1);
        return this.candidatos;
    }
}