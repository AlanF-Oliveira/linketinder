package org.alan.usuarios

import org.alan.model.Empresa
import org.alan.model.Vaga

class VagasCadastradas {
    List<Vaga> vagas;
    VagasCadastradas(List<Empresa> empresas) {
        vagas = [
                new Vaga(
                        titulo: 'Desenvolvedor Backend Java',
                        descricao: 'Vaga para desenvolvimento de sistemas internos em Java e Spring.',
                        estado: 'CE',
                        cidade: 'Fortaleza',
                        competenciasExigidas: ['Java', 'Spring Boot', 'SQL'],
                        empresa: empresas[0]
                ),
                new Vaga(
                        titulo: 'Desenvolvedor Frontend Angular',
                        descricao: 'Vaga para manutenção e evolução do sistema de reservas.',
                        estado: 'SP',
                        cidade: 'São Paulo',
                        competenciasExigidas: ['Angular', 'Node.js', 'MongoDB'],
                        empresa: empresas[1]
                ),
                new Vaga(
                        titulo: 'Engenheiro de Dados Python',
                        descricao: 'Vaga para construção de pipelines de dados em nuvem.',
                        estado: 'MG',
                        cidade: 'Belo Horizonte',
                        competenciasExigidas: ['Python', 'PostgreSQL', 'Docker'],
                        empresa: empresas[2]
                ),
                new Vaga(
                        titulo: 'Desenvolvedor Fullstack React',
                        descricao: 'Vaga para o time de produto digital.',
                        estado: 'BA',
                        cidade: 'Salvador',
                        competenciasExigidas: ['React', 'MongoDB', 'CSS'],
                        empresa: empresas[3]
                ),
                new Vaga(
                        titulo: 'Desenvolvedor Java Sênior',
                        descricao: 'Vaga para arquitetura de sistemas distribuídos.',
                        estado: 'DF',
                        cidade: 'Brasília',
                        competenciasExigidas: ['Java', 'Kubernetes', 'PostgreSQL'],
                        empresa: empresas[4]
                )
        ]
    }
}