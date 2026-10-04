package org.alan.service

import org.alan.database.BancoDeDados
import org.alan.model.Empresa
import spock.lang.Specification

class EmpresaServiceSpec extends Specification {

    BancoDeDadosFalso bancoDeDados = new BancoDeDadosFalso()
    EmpresaService empresaService = new EmpresaService(bancoDeDados)

    def "deve cadastrar empresa e retornar o registro com id"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")
        bancoDeDados.idGerado = 20

        when:
        Empresa resultado = empresaService.salvar(empresa)

        then:
        bancoDeDados.chamadas == 1
        resultado.is(empresa)
        resultado.id == 20
    }

    def "deve informar falha quando o cadastro não gerar id"() {
        given:
        Empresa empresa = new Empresa(nome: "Dogão do Ratão", cnpj: "5678")

        when:
        empresaService.salvar(empresa)

        then:
        bancoDeDados.chamadas == 1
        Exception erro = thrown()
        erro.message == "Falha ao cadastrar empresa"
    }

    private static class BancoDeDadosFalso extends BancoDeDados {
        int chamadas
        int idGerado

        @Override
        int insertEmpresa(Empresa empresa) {
            chamadas++
            if (idGerado > 0) {
                empresa.id = idGerado
            }
            return idGerado
        }
    }
}
