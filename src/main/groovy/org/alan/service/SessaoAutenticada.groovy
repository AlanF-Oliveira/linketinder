package org.alan.service

class SessaoAutenticada {
    final String tipo
    final Object usuario
    final String identificador

    SessaoAutenticada(String tipo, Object usuario, String identificador) {
        this.tipo = tipo
        this.usuario = usuario
        this.identificador = identificador
    }

    boolean ehEmpresa() {
        return tipo == "EMPRESA"
    }

    boolean ehCandidato() {
        return tipo == "CANDIDATO"
    }

    boolean ehRecrutador() {
        return tipo == "RECRUTADOR"
    }
}
