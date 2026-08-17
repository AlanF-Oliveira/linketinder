package org.alan.service

import org.alan.model.Empresa

class EmpresaService{

    List<Empresa> list ;

    EmpresaService(List<Empresa> list) {
        this.list = list
    }

    List<Empresa> salvar (Empresa empresaRequest){
        list.add(empresaRequest)
        return list
    }
}

