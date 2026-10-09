package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinejoy.data.repository.SessaoRepository
import kotlinx.coroutines.launch

@Composable
fun ADMINISTRADOR_Unidades_screen(navController: NavController) {
    val azulEscuro = Color(0xFF12395E)
    val azulMedio = Color(0xFF5B88B5)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    var estado by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var nomeUnidade by remember { mutableStateOf("") }

    var salaId by remember { mutableStateOf("") }
    var salaIdUnidade by remember { mutableStateOf("") }
    var salaNumero by remember { mutableStateOf("") }
    var salaCapacidade by remember { mutableStateOf("") }
    var salaTipo by remember { mutableStateOf("") }

    var sessaoId by remember { mutableStateOf("") }
    var sessaoIdFilme by remember { mutableStateOf("") }
    var sessaoIdSala by remember { mutableStateOf("") }
    var sessaoData by remember { mutableStateOf("") }
    var sessaoHorario by remember { mutableStateOf("") }
    var sessaoIdioma by remember { mutableStateOf("") }
    var sessaoFormato by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cinzaFundo)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        TopoAdminGenerico(
            titulo = "Unidades CineJoy",
            azulEscuro = azulEscuro,
            amarelo = amarelo
        )

        Spacer(modifier = Modifier.height(16.dp))

        val camposUnidade = listOf(
            Triple("Estado", estado, { v: String -> estado = v }),
            Triple("Cidade", cidade, { v: String -> cidade = v }),
            Triple("CEP", cep, { v: String -> cep = v }),
            Triple("Nome da Unidade", nomeUnidade, { v: String -> nomeUnidade = v })
        )

        camposUnidade.forEachIndexed { index, (label, valor, onChange) ->
            CampoAdminGenerico(
                valor = valor,
                aoMudar = onChange,
                placeholder = label,
                cor = if (index % 2 == 0) azulEscuro else azulMedio,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SubTabelaUnidades(
                titulo = "Salas",
                modifier = Modifier.weight(1f),
                azulEscuro = azulEscuro,
                azulMedio = azulMedio,
                amarelo = amarelo
            ) {
                CampoSubTabela("ID", salaId, { salaId = it })
                CampoSubTabela("ID unidade", salaIdUnidade, { salaIdUnidade = it })
                CampoSubTabela("Numero", salaNumero, { salaNumero = it })
                CampoSubTabela("Capacidade", salaCapacidade, { salaCapacidade = it })
                CampoSubTabela("Tipo (vip, normal)", salaTipo, { salaTipo = it })
            }

            SubTabelaUnidades(
                titulo = "Sessoes",
                modifier = Modifier.weight(1f),
                azulEscuro = azulEscuro,
                azulMedio = azulMedio,
                amarelo = amarelo
            ) {
                CampoSubTabela("ID", sessaoId, { sessaoId = it })
                CampoSubTabela("ID filme", sessaoIdFilme, { sessaoIdFilme = it })
                CampoSubTabela("ID sala", sessaoIdSala, { sessaoIdSala = it })
                CampoSubTabela("Data (AAAA-MM-DD)", sessaoData, { sessaoData = it })
                CampoSubTabela("Horario (HH:mm)", sessaoHorario, { sessaoHorario = it })
                CampoSubTabela("Idioma", sessaoIdioma, { sessaoIdioma = it })
                CampoSubTabela("Formato", sessaoFormato, { sessaoFormato = it })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mensagem.isNotBlank()) {
            Text(
                text = mensagem,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        BotoesCrudGenerico(
            onCadastrar = {
                val idFilme = sessaoIdFilme.toIntOrNull()
                val idSala = sessaoIdSala.toIntOrNull()

                if (idFilme == null || idSala == null || sessaoData.isBlank() || sessaoHorario.isBlank()) {
                    mensagem = "Preencha ID filme, ID sala, data e horario para cadastrar a sessao."
                } else {
                    scope.launch {
                        mensagem = "Salvando sessao no banco..."
                        val salvou = runCatching {
                            SessaoRepository.cadastrarSessao(
                                idFilme = idFilme,
                                idSala = idSala,
                                data = sessaoData,
                                horario = sessaoHorario,
                                idioma = sessaoIdioma.ifBlank { "Dublado" },
                                formato = sessaoFormato.ifBlank { "2D" }
                            )
                        }.getOrDefault(false)

                        mensagem = if (salvou) {
                            "Sessao cadastrada com sucesso."
                        } else {
                            "Nao foi possivel cadastrar a sessao. Verifique a API e o banco."
                        }
                    }
                }
            },
            onAlterar = {},
            onExcluir = {},
            onBuscar = {}
        )
    }
}

@Composable
private fun SubTabelaUnidades(
    titulo: String,
    modifier: Modifier = Modifier,
    azulEscuro: Color,
    azulMedio: Color,
    amarelo: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = titulo,
            color = amarelo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6A6A6A), RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFB0B0B0), RoundedCornerShape(12.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            content = content
        )
    }
}

@Composable
private fun CampoSubTabela(
    placeholder: String,
    valor: String,
    aoMudar: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = aoMudar,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.DarkGray,
                fontSize = 11.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color(0xFF12395E),
            unfocusedTextColor = Color(0xFF12395E),
            cursorColor = Color(0xFF12395E)
        )
    )
}
