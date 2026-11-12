package org.alan.model

import java.time.LocalDate

class Recrutador extends Usuario {
    Integer id
    String areaAtuacao
    String cpf
    LocalDate nascimento
    Empresa empresa
    String status = "ATIVO"

    @Override
    String toString() {
        return "$id | $nome | $areaAtuacao | $email | ${empresa?.nome} | $status"
    }
}
