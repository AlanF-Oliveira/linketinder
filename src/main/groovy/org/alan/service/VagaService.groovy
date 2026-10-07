package org.alan.service

import org.alan.dao.VagaDAO
import org.alan.database.BancoDeDados
import org.alan.model.Vaga

class VagaService {

    private final VagaDAO vagaDAO;

    VagaService(VagaDAO vagaDAO) {
        this.vagaDAO = vagaDAO;
    }

    Vaga criarVaga(Vaga vaga, int idEmpresa) {
       vagaDAO.insertVaga(vaga, idEmpresa)
        if(vaga.id == null){
            throw new Exception("Falha ao cadastrar vaga")
        }
         return vaga
    }

    List<Vaga> listarVagas() {
      return vagaDAO.listarVagas()
    }

    Vaga buscarVagaPorId(int idVaga){
      Vaga vaga = vagaDAO.buscarVagaPorId(idVaga)
        if (vaga == null){
            throw new Exception("Vaga não encontrada")
        }
        return vaga
    }

    Vaga atualizarVaga(Vaga vaga){
       boolean atualizou =  vagaDAO.atualizarVaga(vaga)
        if (!atualizou){
            throw new Exception("Vaga não encontrada")
        }
        return vaga
    }

    void deletarVaga(int idVaga) {
        boolean  deletou = vagaDAO.deletarVaga(idVaga)
        if(!deletou){
            throw new Exception("Vaga não encontrada")
        }
    }

}
