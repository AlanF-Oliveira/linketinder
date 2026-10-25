package org.alan

import com.sun.net.httpserver.HttpServer
import org.alan.api.CandidatoHttpHandler
import org.alan.api.EmpresaHttpHandler
import org.alan.api.VagaHttpHandler
import org.alan.controller.CandidatoController
import org.alan.controller.EmpresaController
import org.alan.controller.VagaController
import org.alan.dao.CandidatoDAO
import org.alan.dao.CompetenciaDAO
import org.alan.dao.EmpresaDAO
import org.alan.dao.VagaDAO
import org.alan.database.ConnectionFactory
import org.alan.database.PostgresConnectionFactory
import org.alan.service.CandidatoService
import org.alan.service.EmpresaService
import org.alan.service.VagaService

import java.net.InetSocketAddress

class ApiMain {

    static void main(String[] args) {
        HttpServer servidor = HttpServer.create(
                new InetSocketAddress("localhost", 8080),
                0)
        ConnectionFactory fabrica = new PostgresConnectionFactory()
        CompetenciaDAO competenciaDAO = new CompetenciaDAO(fabrica)
        CandidatoDAO candidatoDAO = new CandidatoDAO(fabrica, competenciaDAO)
        CandidatoService service = new CandidatoService(candidatoDAO)
        CandidatoController controller = new CandidatoController(service)
        servidor.createContext("/candidatos", new CandidatoHttpHandler(controller))

        EmpresaDAO empresaDAO = new EmpresaDAO(fabrica)
        EmpresaService empresaService = new EmpresaService(empresaDAO)
        EmpresaController empresaController = new EmpresaController(empresaService)
        servidor.createContext("/empresas", new EmpresaHttpHandler(empresaController))

        VagaDAO vagaDAO = new VagaDAO(fabrica, competenciaDAO, empresaDAO)
        VagaService vagaService = new VagaService(vagaDAO)
        VagaController vagaController = new VagaController(vagaService, empresaService)
        servidor.createContext("/vagas", new VagaHttpHandler(vagaController))

        servidor.start()
        println "API rodando em http://localhost:8080"
    }
}