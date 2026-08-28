import { Vaga } from '../models/Vaga';
import { empresas } from './empresas';

export const vagas: Vaga[] = [
    new Vaga('Desenvolvedor Java Júnior', 'Vaga para dev júnior com foco em Spring Boot.', ['Java', 'Spring Boot', 'SQL'], empresas[0]),
    new Vaga('Desenvolvedor Frontend', 'Vaga para dev frontend com experiência em Angular.', ['Angular', 'TypeScript', 'CSS'], empresas[1]),
    new Vaga('Engenheiro de Dados', 'Vaga para engenheiro de dados pleno.', ['Python', 'SQL', 'Airflow'], empresas[2]),
];