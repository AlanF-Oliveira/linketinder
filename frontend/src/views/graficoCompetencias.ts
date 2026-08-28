
import { Chart } from 'chart.js/auto';
import { Candidato } from '../models/Candidato';
import { contarCandidatosPorCompetencia } from '../services/GraficoService';

export function criarGraficoCompetencias(canvasId: string, candidatos: Candidato[]) {
    const contagem = contarCandidatosPorCompetencia(candidatos);
    const labels = Object.keys(contagem);
    const valores = Object.values(contagem);

    const canvas = document.getElementById(canvasId) as HTMLCanvasElement;

    new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Candidatos por competência',
                data: valores,
            }],
        },
    });
}