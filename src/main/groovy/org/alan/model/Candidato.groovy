package org.alan.model

import java.time.LocalDate

//import groovy.transform.ToString
//@ToString(includeSuperProperties = true, includePackage = false,includes = ['nome', 'email', 'cep', 'estado', 'descricao', 'cpf', 'idade', 'competencias'])
class Candidato extends Usuario{
    String sobrenome;
    String cpf;
    LocalDate nascimento;
    List<String> competencias = []

    @Override
    public String toString() {
        return "$nome $sobrenome | $nascimento | $email | $cpf | $descricao | $cidade, $estado, $pais | $cep | $competencias"
    }
}