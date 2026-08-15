package org.alan.usuarios

import org.alan.Model.Candidato
import org.alan.Model.Empresa

class UsuariosCadastrados {
    List<Candidato> candidatos = [
            new Candidato(
                    nome: "Alan Oliveira",
                    email: "alana@mail.com",
                    cep: "60450340",
                    estado: "Ceará",
                    descricao: "Desenvolvedor Java",
                    cpf: "10403796542",
                    idade: 32,
                    competencias: ["Java", "Spring Boot", "PostgreSQL"]
            ),
            new Candidato(
                    nome: "Beatriz Souza",
                    email: "beatriz@mail.com",
                    cep: "01310100",
                    estado: "São Paulo",
                    descricao: "Desenvolvedora frontend",
                    cpf: "11122233344",
                    idade: 22,
                    competencias: ["Angular", "TypeScript", "CSS"]
            ),
            new Candidato(
                    nome: "Carlos Mendes",
                    email: "carlos@mail.com",
                    cep: "30130010",
                    estado: "Minas Gerais",
                    descricao: "Engenheiro de dados",
                    cpf: "22233344455",
                    idade: 35,
                    competencias: ["Python", "SQL", "Airflow"]
            ),
            new Candidato(
                    nome: "Daniela Ramos",
                    email: "daniela@mail.com",
                    cep: "40010000",
                    estado: "Bahia",
                    descricao: "Desenvolvedora fullstack",
                    cpf: "33344455566",
                    idade: 27,
                    competencias: ["React", "Node.js", "MongoDB"]
            ),
            new Candidato(
                    nome: "Eduardo Lima",
                    email: "eduardo@mail.com",
                    cep: "70040010",
                    estado: "Distrito Federal",
                    descricao: "Analista de sistemas",
                    cpf: "44455566677",
                    idade: 44,
                    competencias: ["Java", "Docker", "Kubernetes"]
            )

    ]
    List<Empresa> empresas = [
            new Empresa(
                    nome: "Arroz-Gostoso",
                    email: "contato@arrozgostoso.com",
                    cep: "60175047",
                    estado: "Ceará",
                    descricao: "Empresa do setor alimentício.",
                    cnpj: "12345678000199",
                    pais: "Brasil",
                    competenciasDesejadas: ["Java", "Spring Framework", "SQL"]
            ),
            new Empresa(
                    nome: "Império do Boliche",
                    email: "rh@imperiodoboliche.com",
                    cep: "01452000",
                    estado: "São Paulo",
                    descricao: "Rede de casas de entretenimento e lazer.",
                    cnpj: "98765432000111",
                    pais: "Brasil",
                    competenciasDesejadas: ["Angular", "Node.js", "MongoDB"]
            ),
            new Empresa(
                    nome: "TechNova Soluções",
                    email: "contato@technova.com",
                    cep: "30140071",
                    estado: "Minas Gerais",
                    descricao: "Consultoria em transformação digital.",
                    cnpj: "11223344000155",
                    pais: "Brasil",
                    competenciasDesejadas: ["Python", "AWS", "Docker"]
            ),
            new Empresa(
                    nome: "Boliche Champions",
                    email: "contato@bolichechampions.com",
                    cep: "40110010",
                    estado: "Bahia",
                    descricao: "Franquia de centros de boliche.",
                    cnpj: "22334455000166",
                    pais: "Brasil",
                    competenciasDesejadas: ["React", "Firebase", "UX Design"]
            ),
            new Empresa(
                    nome: "DataFlow Analytics",
                    email: "rh@dataflow.com",
                    cep: "70070100",
                    estado: "Distrito Federal",
                    descricao: "Empresa de análise de dados e BI.",
                    cnpj: "33445566000177",
                    pais: "Brasil",
                    competenciasDesejadas: ["Java", "Kubernetes", "PostgreSQL"]
            )
    ]


}