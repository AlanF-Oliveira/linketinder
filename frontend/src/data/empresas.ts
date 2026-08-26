import { Empresa } from '../models/Empresa';

export const empresas: Empresa[] = [
    new Empresa(
        'Arroz Gostoso',
        'contato@arrozgostoso.com',
        '12345678000199',
        'Brasil',
        'Ceará',
        '60175047',
        'Empresa do setor alimentício.',
        ['Java', 'Spring Framework', 'SQL']
    ),
    new Empresa(
        'Império do Boliche',
        'rh@imperiodoboliche.com',
        '98765432000111',
        'Brasil',
        'São Paulo',
        '01452000',
        'Rede de casas de entretenimento e lazer.',
        ['Angular', 'Node.js', 'MongoDB']
    ),
    new Empresa(
        'TechNova Soluções',
        'contato@technova.com',
        '11223344000155',
        'Brasil',
        'Minas Gerais',
        '30140071',
        'Consultoria em transformação digital.',
        ['Python', 'AWS', 'Docker']
    ),
    new Empresa(
        'Boliche Champions',
        'contato@bolichechampions.com',
        '22334455000166',
        'Brasil',
        'Bahia',
        '40110010',
        'Franquia de centros de boliche.',
        ['React', 'Firebase', 'UX Design']
    ),
    new Empresa(
        'DataFlow Analytics',
        'rh@dataflow.com',
        '33445566000177',
        'Brasil',
        'Distrito Federal',
        '70070100',
        'Empresa de análise de dados e BI.',
        ['Java', 'Kubernetes', 'PostgreSQL']
    ),
];