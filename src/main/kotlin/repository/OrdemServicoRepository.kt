package data.repository

import data.model.Ordem
import Database
import java.sql.PreparedStatement
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrdemServicoRepository {

    fun adicionar(os: Ordem) {
        // Consultar o último número de OS
        val stmt = Database.connection.createStatement()
        val rs = stmt.executeQuery("SELECT MAX(numeroOS) FROM ordem_servico")

        // Verificar se há registros e pegar o maior número de OS
        val numeroOS = if (rs.next() && rs.getInt(1) != 0) {
            rs.getInt(1) + 1 // Incrementa 1 no último número encontrado
        } else {
            1 // Se não houver registros, começa com 1
        }

        println("Número da OS gerado: $numeroOS") // Depuração

        // Inserir a nova Ordem de Serviço com o número de OS incrementado
        val sql = """
    INSERT INTO ordem_servico (nome, celular, marcaNotebook, defeito, observacoes, numeroOS, data)
    VALUES (?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        val stmtInsert: PreparedStatement = Database.connection.prepareStatement(sql)
        stmtInsert.setString(1, os.nome)
        stmtInsert.setString(2, os.celular)
        stmtInsert.setString(3, os.marcaNotebook)
        stmtInsert.setString(4, os.defeito)
        stmtInsert.setString(5, os.observacoes)
        stmtInsert.setInt(6, numeroOS) // Atribui o número de OS
        stmtInsert.setString(7, os.data.toString()) // Atribui a data convertida em String
        stmtInsert.executeUpdate()
    }

    fun atualizar(os: Ordem) {
        val sql = """
        UPDATE ordem_servico
        SET nome = ?, celular = ?, marcaNotebook = ?, defeito = ?, observacoes = ?, data = ?, numeroOS = ?
        WHERE id = ?
    """.trimIndent()

        val stmt = Database.connection.prepareStatement(sql)
        stmt.setString(1, os.nome)
        stmt.setString(2, os.celular)
        stmt.setString(3, os.marcaNotebook)
        stmt.setString(4, os.defeito)
        stmt.setString(5, os.observacoes)
        stmt.setString(6, os.data.toString())
        stmt.setInt(7, os.numeroOS) // Adicione esta linha
        stmt.setInt(8, os.id)
        stmt.executeUpdate()
    }

    fun listar(): List<Ordem> {
        val lista = mutableListOf<Ordem>()
        val rs = Database.connection.createStatement().executeQuery("SELECT * FROM ordem_servico")

        while (rs.next()) {
            val dataString = rs.getString("data")
            val data = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            lista.add(
                Ordem(
                    id = rs.getInt("id"),
                    nome = rs.getString("nome"),
                    celular = rs.getString("celular"),
                    marcaNotebook = rs.getString("marcaNotebook"),
                    defeito = rs.getString("defeito"),
                    observacoes = rs.getString("observacoes"),
                    data = data,
                    numeroOS = rs.getInt("numeroOS") // Adicione esta linha
                )
            )
        }

        return lista
    }
}
