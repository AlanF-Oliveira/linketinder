import { Recrutador } from '../models/Recrutador';
import { empresas } from './empresas';

export const recrutadores: Recrutador[] = [
    new Recrutador(
        'Marina Costa',
        'Tecnologia',
        'Recrutadora focada em engenharia de software.',
        'marina@arrozgostoso.com',
        '55566677788',
        '1990-04-12',
        'recrutador123',
        empresas[0]
    ),
    new Recrutador(
        'Rafael Martins',
        'Dados',
        'Atua na seleção de profissionais de dados.',
        'rafael@technova.com',
        '66677788899',
        '1988-09-20',
        'recrutador123',
        empresas[2]
    )
];
