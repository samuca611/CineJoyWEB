package com.example.cinejoy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Relatorio(
    val id: Int,
    val data: String,
    val titulo: String,
    val detalhes: String,
    val expandido: Boolean = false
)

@Composable
fun ADMINISTRADOR_Relatorio_screen(navController: NavController) {

    val azulEscuro = Color(0xFF12395E)
    val azulMedio = Color(0xFF5B88B5)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    val relatorios = remember {
        mutableStateListOf(
            Relatorio(
                1,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 120\nReceita: R$ 2.400,00\nFilme mais assistido: Barbie"
            ),
            Relatorio(
                2,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 85\nReceita: R$ 1.700,00\nFilme mais assistido: Avatar"
            ),
            Relatorio(
                3,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 200\nReceita: R$ 4.000,00\nFilme mais assistido: Mario"
            ),
            Relatorio(
                4,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 60\nReceita: R$ 1.200,00\nFilme mais assistido: Vingadores"
            ),
            Relatorio(
                5,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 175\nReceita: R$ 3.500,00\nFilme mais assistido: Barbie"
            ),
            Relatorio(
                6,
                "xx/xx/xxxx",
                "Relatório data xx/xx/xxxx",
                "Ingressos vendidos: 90\nReceita: R$ 1.800,00\nFilme mais assistido: Avatar"
            )
        )
    }

    var relatorioEmEdicao by remember { mutableStateOf<Relatorio?>(null) }
    var textoEdicao by remember { mutableStateOf("") }
    var mostrarDialogo by remember { mutableStateOf(false) }

    var mostrarNovoRelatorio by remember { mutableStateOf(false) }
    var novoTitulo by remember { mutableStateOf("") }
    var novaData by remember { mutableStateOf("") }
    var novoDetalhe by remember { mutableStateOf("") }

    Scaffold(
        containerColor = cinzaFundo,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarNovoRelatorio = true },
                containerColor = amarelo,
                contentColor = azulEscuro,
                shape = CircleShape
            ) {
                Text(
                    text = "+",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            BarraInferiorRelatorio(
                azulEscuro = azulEscuro,
                amarelo = amarelo
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            TopoAdminRelatorio(
                azulEscuro = azulEscuro,
                amarelo = amarelo
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(relatorios) { index, relatorio ->
                    ItemRelatorio(
                        relatorio = relatorio,
                        corFundo = if (index % 2 == 0) azulMedio else azulEscuro,
                        amarelo = amarelo,
                        onToggle = {
                            val novaLista = relatorios.toMutableList()
                            novaLista[index] = relatorio.copy(expandido = !relatorio.expandido)
                            relatorios.clear()
                            relatorios.addAll(novaLista)
                        },
                        onEngrenagem = {
                            relatorioEmEdicao = relatorio
                            textoEdicao = relatorio.detalhes
                            mostrarDialogo = true
                        }
                    )
                }
            }
        }
    }

    if (mostrarDialogo && relatorioEmEdicao != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = {
                Text(
                    text = "Editar relatório",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Conteúdo do relatório:",
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = textoEdicao,
                        onValueChange = { textoEdicao = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        label = { Text("Detalhes") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val idx = relatorios.indexOfFirst { it.id == relatorioEmEdicao!!.id }

                        if (idx >= 0) {
                            relatorios[idx] = relatorioEmEdicao!!.copy(detalhes = textoEdicao)
                        }

                        mostrarDialogo = false
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        relatorios.removeIf { it.id == relatorioEmEdicao!!.id }
                        mostrarDialogo = false
                    }
                ) {
                    Text(
                        text = "Excluir",
                        color = Color(0xFFF44336)
                    )
                }
            }
        )
    }

    if (mostrarNovoRelatorio) {
        AlertDialog(
            onDismissRequest = { mostrarNovoRelatorio = false },
            title = {
                Text(
                    text = "Novo Relatório",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = novoTitulo,
                        onValueChange = { novoTitulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = novaData,
                        onValueChange = { novaData = it },
                        label = { Text("Data (dd/mm/aaaa)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = novoDetalhe,
                        onValueChange = { novoDetalhe = it },
                        label = { Text("Detalhes") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val novoId = (relatorios.maxOfOrNull { it.id } ?: 0) + 1

                        relatorios.add(
                            Relatorio(
                                novoId,
                                novaData,
                                novoTitulo,
                                novoDetalhe
                            )
                        )

                        novoTitulo = ""
                        novaData = ""
                        novoDetalhe = ""
                        mostrarNovoRelatorio = false
                    }
                ) {
                    Text("Criar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarNovoRelatorio = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ItemRelatorio(
    relatorio: Relatorio,
    corFundo: Color,
    amarelo: Color,
    onToggle: () -> Unit,
    onEngrenagem: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onEngrenagem,
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF12395E), CircleShape)
            ) {
                Text(
                    text = "",
                    fontSize = 16.sp,
                    color = amarelo
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(corFundo, RoundedCornerShape(24.dp))
                    .clickable { onToggle() },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = relatorio.titulo,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = relatorio.expandido,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 44.dp, top = 4.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = relatorio.detalhes,
                    color = Color(0xFF12395E),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun TopoAdminRelatorio(
    azulEscuro: Color,
    amarelo: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(azulEscuro)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(amarelo, RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Relatórios",
                color = amarelo,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BarraInferiorRelatorio(
    azulEscuro: Color,
    amarelo: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(azulEscuro)
            .padding(horizontal = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {

            }
        ) {
            Text(
                text = "☰",
                fontSize = 26.sp,
                color = amarelo
            )
        }

        IconButton(
            onClick = {

            }
        ) {
            Text(
                text = "",
                fontSize = 26.sp,
                color = Color.White
            )
        }
    }
}