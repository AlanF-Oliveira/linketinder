package org.alan.model

class Empresa extends Usuario{
    String cnpj

    @Override
    public String toString() {
        return "$nome | $email | $cnpj | $cidade, $estado, $pais | $cep | $descricao"
    }
}

