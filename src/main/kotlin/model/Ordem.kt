package data.model

import java.time.LocalDate

data class Ordem(
    val id: Int = 0,
    val nome: String,
    val celular: String,
    val marcaNotebook: String,
    val defeito: String,
    val observacoes: String,
    val data: LocalDate,
    val numeroOS: Int = 0
)
