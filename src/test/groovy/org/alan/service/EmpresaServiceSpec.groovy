package org.alan.service

import org.alan.model.Empresa
import spock.lang.Specification

class EmpresaServiceSpec extends Specification{

    List<Empresa> listaEmpresas;
    EmpresaService empresaService;

    def setup(){
        listaEmpresas = []
        empresaService = new EmpresaService(listaEmpresas);
    }

    def "deve cadastrar empresa com sucesso"(){

        given:
        Empresa empresa = new Empresa(
                nome: "Dogão do ratão",
                email: "dogratao@ratoes.com",
                cnpj: "5678",
                pais: "Brasil",
                estado: "São Paulo",
                cep: "123",
                descricao: "O melhor podrão de São Paulo",
                competenciasDesejadas: ["Cozinhar", "Atender", "Lavar pratos"]
        )

        when:
        empresaService.salvar(empresa)

        then:
        listaEmpresas.size() == 1
        listaEmpresas[0].nome == "Dogão do ratão"
        listaEmpresas[0].cnpj == "5678"
    }
}