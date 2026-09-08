package org.alan.usuarios

import org.alan.model.Candidato
import org.alan.model.Empresa
import java.time.LocalDate

class UsuariosCadastrados {
    List<Candidato> candidatos = [
            new Candidato(
                    nome: "Alan",
                    sobrenome: "Oliveira",
                    nascimento: LocalDate.of(1994, 3, 15),
                    email: "alana@mail.com",
                    cpf: "10403796542",
                    descricao: "Desenvolvedor Java",
                    pais: "Brasil",
                    estado: "CE",
                    cidade: "Fortaleza",
                    cep: "60450-340",
                    senha: "senha123",
                    competencias: ["Java", "Spring Boot", "PostgreSQL"]
            ),
            new Candidato(
                    nome: "Beatriz",
                    sobrenome: "Souza",
                    nascimento: LocalDate.of(2004, 7, 22),
                    email: "beatriz@mail.com",
                    cpf: "11122233344",
                    descricao: "Desenvolvedora frontend",
                    pais: "Brasil",
                    estado: "SP",
                    cidade: "São Paulo",
                    cep: "01310-100",
                    senha: "senha123",
                    competencias: ["Angular", "TypeScript", "CSS"]
            ),
            new Candidato(
                    nome: "Carlos",
                    sobrenome: "Mendes",
                    nascimento: LocalDate.of(1991, 1, 10),
                    email: "carlos@mail.com",
                    cpf: "22233344455",
                    descricao: "Engenheiro de dados",
                    pais: "Brasil",
                    estado: "MG",
                    cidade: "Belo Horizonte",
                    cep: "30130-010",
                    senha: "senha123",
                    competencias: ["Python", "SQL", "Airflow"]
            ),
            new Candidato(
                    nome: "Daniela",
                    sobrenome: "Ramos",
                    nascimento: LocalDate.of(1999, 11, 5),
                    email: "daniela@mail.com",
                    cpf: "33344455566",
                    descricao: "Desenvolvedora fullstack",
                    pais: "Brasil",
                    estado: "BA",
                    cidade: "Salvador",
                    cep: "40010-000",
                    senha: "senha123",
                    competencias: ["React", "Node.js", "MongoDB"]
            ),
            new Candidato(
                    nome: "Eduardo",
                    sobrenome: "Lima",
                    nascimento: LocalDate.of(1982, 6, 30),
                    email: "eduardo@mail.com",
                    cpf: "44455566677",
                    descricao: "Analista de sistemas",
                    pais: "Brasil",
                    estado: "DF",
                    cidade: "Brasília",
                    cep: "70040-010",
                    senha: "senha123",
                    competencias: ["Java", "Docker", "Kubernetes"]
            )
    ]

    List<Empresa> empresas = [
            new Empresa(
                    nome: "Arroz Gostoso2",
                    email: "contato@arrozgostoso.com",
                    cnpj: "312345678000199",
                    descricao: "Empresa do setor alimentício.",
                    pais: "Brasil",
                    estado: "CE",
                    cidade: "Fortaleza",
                    cep: "60175-047",
                    senha: "senha123"
            ),
            new Empresa(
                    nome: "Império do Boliche",
                    email: "rh@imperiodoboliche.com",
                    cnpj: "98765432000111",
                    descricao: "Rede de casas de entretenimento e lazer.",
                    pais: "Brasil",
                    estado: "SP",
                    cidade: "São Paulo",
                    cep: "01452-000",
                    senha: "senha123"
            ),
            new Empresa(
                    nome: "TechNova Soluções",
                    email: "contato@technova.com",
                    cnpj: "11223344000155",
                    descricao: "Consultoria em transformação digital.",
                    pais: "Brasil",
                    estado: "MG",
                    cidade: "Belo Horizonte",
                    cep: "30140-071",
                    senha: "senha123"
            ),
            new Empresa(
                    nome: "Boliche Champions",
                    email: "contato@bolichechampions.com",
                    cnpj: "22334455000166",
                    descricao: "Franquia de centros de boliche.",
                    pais: "Brasil",
                    estado: "BA",
                    cidade: "Salvador",
                    cep: "40110-010",
                    senha: "senha123"
            ),
            new Empresa(
                    nome: "DataFlow Analytics",
                    email: "rh@dataflow.com",
                    cnpj: "33445566000177",
                    descricao: "Empresa de análise de dados e BI.",
                    pais: "Brasil",
                    estado: "DF",
                    cidade: "Brasília",
                    cep: "70070-100",
                    senha: "senha123"
            )
    ]
}