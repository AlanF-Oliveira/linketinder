import { Candidato } from "../models/Candidato";

export function contarCandidatosPorCompetencia(candidatos: Candidato[]): Record<string, number> {
    const contagem: Record<string, number> = {};

    for (const candidato of candidatos) {
        for (const competencia of candidato.competencias) {
            contagem[competencia] = (contagem[competencia] || 0) + 1;
        }
    }

    return contagem;
}