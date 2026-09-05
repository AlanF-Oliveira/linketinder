package org.alan.service

import org.alan.model.Vaga

class VagaService {

    List<Vaga> list;

    VagaService(List<Vaga> list) {
        this.list = list;
    }

    List<Vaga> criarVaga(Vaga vaga) {
        list.add(vaga);
        return list;
    }

    List<Vaga> listarVagas() {
        if (list.isEmpty()) {
            return new ArrayList<>()
        }
        return new ArrayList<>(list);
    }

    void deletarVaga(int id) {
        int index = list.findIndexOf { it.id == id }
        if (index == -1) {
            throw new Exception("ID não encontrado")
        }
        list.remove(index)
    }

}
