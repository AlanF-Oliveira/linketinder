package org.alan.model

class Vaga{
    private static int nextId = 0
    int id;
    String titulo;
    String descricao;
    List <String>competenciasExigidas = [];
    Empresa empresa;

    Vaga() {
        id = ++nextId
    }

    @Override
    public String toString() {
        return "$id | $titulo | $descricao | $competenciasExigidas"
    }
}