package org.alan.usuarios

import org.alan.model.Empresa
import org.alan.model.Vaga

class VagasCadastradas {
    List<Vaga> vagas;
    VagasCadastradas(List<Empresa> empresa){
        vagas =  [new Vaga(titulo: 'Desenvolvedor Java Júnior', descricao: 'Vaga para dev júnior com foco em Spring Boot.', competenciasExigidas: ['Java', 'Spring Boot', 'SQL'], empresa: empresa[0]),
                  new Vaga(titulo: 'Desenvolvedor Frontend', descricao: 'Vaga para dev frontend com experiência em Angular.', competenciasExigidas: ['Angular', 'TypeScript', 'CSS'], empresa: empresa[1]),
                  new Vaga(titulo: 'Engenheiro de Dados', descricao: 'Vaga para engenheiro de dados pleno.', competenciasExigidas: ['Python', 'SQL', 'Airflow'], empresa: empresa[2]),
        ];
    }
}
