package org.alan.service

import org.alan.database.BancoDeDados
import org.alan.model.Vaga

class VagaService {

    BancoDeDados bd;

    VagaService(BancoDeDados bd) {
        this.bd = bd;
    }

    Vaga criarVaga(Vaga vaga, int idEmpresa) {
       bd.insertVaga(vaga, idEmpresa)
        if(vaga.id == null){
            throw new Exception("Falha ao cadastrar vaga")
        }
         return vaga
    }

    List<Vaga> listarVagas() {
      return bd.listarVagas()
    }

    Vaga buscarVagaPorId(int idVaga){
      Vaga vaga = bd.buscarVagaPorId(idVaga)
        if (vaga == null){
            throw new Exception("Vaga não encontrada")
        }
        return vaga
    }

    Vaga atualizarVaga(Vaga vaga){
       boolean atualizou =  bd.atualizarVaga(vaga)
        if (!atualizou){
            throw new Exception("Vaga não encontrada")
        }
        return vaga
    }

    void deletarVaga(int idVaga) {
        boolean  deletou = bd.deletarVaga(idVaga)
        if(!deletou){
            throw new Exception("Vaga não encontrada")
        }
    }

}
