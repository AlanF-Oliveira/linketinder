package org.alan.service

import org.alan.model.Empresa

class EmpresaService{

    List<Empresa> list ;

    EmpresaService(List<Empresa> list) {
        this.list = list;
    }

    List<Empresa> salvar (Empresa empresaRequest){
        list.add(empresaRequest)
        return list;
    }

    Empresa buscarPorCnpj(String cnpj){
        Empresa empresa = list.find{it.cnpj == cnpj}
        if(empresa == null){
            throw new Exception("Empresa não encontrada")
        }
        return empresa
    }

    void deletarEmpresa(String cnpj){
       def index =  list.findIndexOf {it.cnpj == cnpj}
        if(index != -1 ){
            list.removeAt(index);
        }
    }
}


