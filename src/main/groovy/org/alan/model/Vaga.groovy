package org.alan.model

class Vaga{
    Integer id;
    String titulo;
    String descricao;
    String estado;
    String cidade;
    List <String>competenciasExigidas = [];
    Empresa empresa;

    @Override
    public String toString() {
        return "$titulo | $descricao | $estado | $cidade | $competenciasExigidas"
    }
}