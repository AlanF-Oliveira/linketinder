package org.alan.Model

class Empresa extends Usuario{
    String cnpj
    String pais;
    List<String> competenciasDesejadas = []

    @Override
    public String toString() {
        return "$nome | $email | $cnpj | $pais | $estado | $cep | $descricao | $competenciasDesejadas"
    }
}

