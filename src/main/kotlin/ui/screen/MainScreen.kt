package ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.Ordem
import data.repository.OrdemServicoRepository
import java.time.LocalDate

@Composable
fun MainScreen() {
    val repo = remember { OrdemServicoRepository() }

    var nome by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var defeito by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    var lista by remember { mutableStateOf(repo.listar()) }
    var modoEdicaoId by remember { mutableStateOf<Int?>(null) }
    var mostrarOS by remember { mutableStateOf(false) }

    // Data atual para inserção automática
    val dataAtual = LocalDate.now()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Feito por Marco Almeida Fameli",
                color = Color.Red,
                style = MaterialTheme.typography.subtitle2
            )
        }

        TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
        TextField(value = celular, onValueChange = { celular = it }, label = { Text("Celular") })
        TextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca do Notebook") })
        TextField(value = defeito, onValueChange = { defeito = it }, label = { Text("Defeito") })
        TextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text("Observações") })

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if (modoEdicaoId == null) {
                    repo.adicionar(
                        Ordem(
                            nome = nome,
                            celular = celular,
                            marcaNotebook = marca,
                            defeito = defeito,
                            observacoes = observacoes,
                            data = dataAtual // Data automaticamente preenchida
                        )
                    )
                } else {
                    repo.atualizar(
                        Ordem(
                            id = modoEdicaoId!!,
                            nome = nome,
                            celular = celular,
                            marcaNotebook = marca,
                            defeito = defeito,
                            observacoes = observacoes,
                            data = dataAtual // Atualiza a data também
                        )
                    )
                    modoEdicaoId = null
                }

                lista = repo.listar()
                nome = ""
                celular = ""
                marca = ""
                defeito = ""
                observacoes = ""
            }) {
                Text(if (modoEdicaoId == null) "Salvar Ordem de Serviço" else "Salvar Alterações")
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (modoEdicaoId != null) {
                Button(onClick = {
                    nome = ""
                    celular = ""
                    marca = ""
                    defeito = ""
                    observacoes = ""
                    modoEdicaoId = null
                }) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            Button(
                onClick = { mostrarOS = !mostrarOS },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF009688),
                    contentColor = Color.White
                )
            ) {
                Text(if (mostrarOS) "Ocultar OS Salvas" else "Mostrar OS Salvas")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mostrarOS) {
            Text("Ordens de Serviço Salvas:", style = MaterialTheme.typography.h6)

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(lista) { os ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                nome = os.nome
                                celular = os.celular
                                marca = os.marcaNotebook
                                defeito = os.defeito
                                observacoes = os.observacoes
                                modoEdicaoId = os.id
                            },
                        elevation = 5.dp,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Número da OS: ${os.numeroOS}", style = MaterialTheme.typography.h6)
                            Text("Data: ${os.data}", style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Nome: ${os.nome}", style = MaterialTheme.typography.h6)
                            Text("Marca: ${os.marcaNotebook}", style = MaterialTheme.typography.body2)
                            Text("Defeito: ${os.defeito}", style = MaterialTheme.typography.body2)
                            Text("Observações: ${os.observacoes}", style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                nome = os.nome
                                celular = os.celular
                                marca = os.marcaNotebook
                                defeito = os.defeito
                                observacoes = os.observacoes
                                modoEdicaoId = os.id
                            }) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }
}
