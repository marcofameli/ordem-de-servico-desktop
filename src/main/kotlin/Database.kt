import java.sql.Connection
import java.sql.DriverManager

object Database {
    val connection: Connection by lazy {
        DriverManager.getConnection("jdbc:sqlite:ordem_servico.db").apply {
            createTablesIfNotExists()
        }
    }

    private fun Connection.createTablesIfNotExists() {
        createStatement().execute(
            """
        CREATE TABLE IF NOT EXISTS ordem_servico (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            celular TEXT NOT NULL,
            marcaNotebook TEXT NOT NULL,
            defeito TEXT NOT NULL,
            observacoes TEXT,
            numeroOS INTEGER NOT NULL,
            data TEXT NOT NULL DEFAULT (DATE('now'))
        );
        """.trimIndent()
        )
    }
}
