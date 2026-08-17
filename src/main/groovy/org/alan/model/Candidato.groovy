package org.alan.model

//import groovy.transform.ToString
//@ToString(includeSuperProperties = true, includePackage = false,includes = ['nome', 'email', 'cep', 'estado', 'descricao', 'cpf', 'idade', 'competencias'])
class Candidato extends Usuario{

    String cpf;
    int idade;
    List<String> competencias = []

    @Override
    public String toString() {
        return "$nome | $idade | $email | $cpf | $descricao | $estado | $cep | $competencias"
    }
}