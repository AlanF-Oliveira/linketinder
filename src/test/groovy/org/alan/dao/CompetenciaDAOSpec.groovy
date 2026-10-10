package org.alan.dao

import org.alan.database.ConnectionFactory
import spock.lang.Specification

import java.sql.Connection
import java.sql.PreparedStatement

class CompetenciaDAOSpec extends Specification {

    def "deve deletar competencia usando a fabrica recebida"() {
        given:
        ConnectionFactory connectionFactory = Mock()
        Connection connection = Mock()
        PreparedStatement statement = Mock()
        CompetenciaDAO competenciaDAO = new CompetenciaDAO(connectionFactory)

        when:
        boolean deletou = competenciaDAO.deletarCompetencia(1)

        then:
        1 * connectionFactory.criarConexao() >> connection
        1 * connection.prepareStatement("DELETE FROM competencias WHERE id = ?") >> statement
        1 * statement.setInt(1, 1)
        1 * statement.executeUpdate() >> 1
        1 * connection.close()
        deletou
    }
}