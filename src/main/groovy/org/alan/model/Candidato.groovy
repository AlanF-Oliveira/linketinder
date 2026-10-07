package org.alan.model

import java.time.LocalDate


class Candidato extends Usuario{
    Integer id;
    String sobrenome;
    String cpf;
    LocalDate nascimento;
    List<String> competencias = []

    @Override
    public String toString() {
        return "$id | $nome $sobrenome | $nascimento | $email | $cpf | $descricao | $cidade, $estado, $pais | $cep | $competencias"
    }
}