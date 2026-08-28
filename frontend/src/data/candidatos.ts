import { Candidato } from '../models/Candidato';

export const candidatos: Candidato[] = [
    new Candidato(
        'Alan Oliveira',
        'alana@mail.com',
        '60450340',
        'Ceará',
        'Desenvolvedor Java',
        '10403796542',
        32,
        'Bacharel em Ciência da Computação',
        ['Java', 'Spring Boot', 'PostgreSQL']
    ),
    new Candidato(
        'Beatriz Souza',
        'beatriz@mail.com',
        '01310100',
        'São Paulo',
        'Desenvolvedora frontend',
        '11122233344',
        22,
        'Técnico em Informática',
        ['Angular', 'TypeScript', 'CSS']
    ),
    new Candidato(
        'Carlos Mendes',
        'carlos@mail.com',
        '30130010',
        'Minas Gerais',
        'Engenheiro de dados',
        '22233344455',
        35,
        'Bacharel em Engenharia da Computação',
        ['Python', 'SQL', 'Airflow']
    ),
    new Candidato(
        'Daniela Ramos',
        'daniela@mail.com',
        '40010000',
        'Bahia',
        'Desenvolvedora fullstack',
        '33344455566',
        27,
        'Bacharel em Sistemas de Informação',
        ['React', 'Node.js', 'MongoDB']
    ),
    new Candidato(
        'Eduardo Lima',
        'eduardo@mail.com',
        '70040010',
        'Distrito Federal',
        'Analista de sistemas',
        '44455566677',
        44,
        'Pós-graduação em Gestão de TI',
        ['Java', 'Docker', 'Kubernetes']
    ),
];