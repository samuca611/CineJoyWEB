package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.data.model.LocalizacaoCineJoy
import com.example.cinejoy.data.model.Unidade

@Composable
fun UsuarioUnidadesScreen(
    unidades: List<Unidade>,
    unidadeSelecionada: Unidade? = null,
    onUnidadeSelecionada: (Unidade) -> Unit = {},
    onAplicarFiltroClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    var estadoSelecionado by remember(unidadeSelecionada) {
        mutableStateOf(
            unidadeSelecionada?.let { "${it.estado} - ${it.siglaEstado}" }.orEmpty()
        )
    }

    var cidadeSelecionada by remember(unidadeSelecionada) {
        mutableStateOf(unidadeSelecionada?.cidade.orEmpty())
    }

    var unidadeEscolhida by remember(unidadeSelecionada) {
        mutableStateOf(unidadeSelecionada)
    }

    var mensagem by remember { mutableStateOf("") }

    val estadoObj = LocalizacaoCineJoy.estadoPorNomeCompleto(estadoSelecionado)

    val cidades = estadoObj?.let {
        LocalizacaoCineJoy.cidadesDoEstado(it.id)
    } ?: emptyList()

    val unidadesFiltradas = unidades.filter { unidade ->
        "${unidade.estado} - ${unidade.siglaEstado}" == estadoSelecionado &&
                unidade.cidade == cidadeSelecionada
    }

    val podeAplicar = estadoSelecionado.isNotBlank() &&
            cidadeSelecionada.isNotBlank() &&
            unidadeEscolhida != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF12395E), Color(0xFF2F7EC7))
                )
            )
    ) {
        TopoCineJoyUsuario(onLogoClick = onLogoClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CampoSelecaoUnidadeCineJoy(
                titulo = "Estado",
                valor = estadoSelecionado,
                placeholder = "Escolha o estado",
                opcoes = LocalizacaoCineJoy.estados.map { it.nomeCompleto },
                onSelecionar = { estado ->
                    estadoSelecionado = estado
                    cidadeSelecionada = ""
                    unidadeEscolhida = null
                    mensagem = ""
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            CampoSelecaoUnidadeCineJoy(
                titulo = "Cidade",
                valor = cidadeSelecionada,
                placeholder = if (estadoSelecionado.isBlank()) "Escolha um estado primeiro" else "Escolha a cidade",
                opcoes = cidades.map { it.nome },
                habilitado = estadoSelecionado.isNotBlank(),
                onSelecionar = { cidade ->
                    cidadeSelecionada = cidade
                    unidadeEscolhida = null
                    mensagem = ""
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            CampoSelecaoUnidadeCineJoy(
                titulo = "Unidade",
                valor = unidadeEscolhida?.nome.orEmpty(),
                placeholder = if (cidadeSelecionada.isBlank()) "Escolha uma cidade primeiro" else "Escolha a unidade CineJoy",
                opcoes = unidadesFiltradas.map { it.nome }.distinct(),
                habilitado = cidadeSelecionada.isNotBlank(),
                onSelecionar = { nomeUnidade ->
                    unidadeEscolhida = unidadesFiltradas.find { it.nome == nomeUnidade }
                    mensagem = ""
                }
            )

            if (unidadeEscolhida != null) {
                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFF12395E), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "Endereço da loja",
                            color = Color(0xFF12395E),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = unidadeEscolhida?.endereco ?: "Shopping CineJoy Central",
                            color = Color.DarkGray,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            if (mensagem.isNotBlank()) {
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .height(42.dp)
                    .background(
                        if (podeAplicar) Color(0xFF12395E) else Color(0xFF6A7D91),
                        RoundedCornerShape(0.dp)
                    )
                    .clickable {
                        if (podeAplicar) {
                            unidadeEscolhida?.let { onUnidadeSelecionada(it) }
                            onAplicarFiltroClick()
                        } else {
                            mensagem = "Escolha estado, cidade e unidade antes de aplicar o filtro."
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aplicar filtro",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .height(42.dp)
                    .background(Color(0xFFC5C600), RoundedCornerShape(0.dp))
                    .clickable { onVoltarClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Voltar",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}

@Composable
fun CampoSelecaoUnidadeCineJoy(
    titulo: String,
    valor: String,
    placeholder: String,
    opcoes: List<String>,
    habilitado: Boolean = true,
    onSelecionar: (String) -> Unit = {}
) {
    var aberto by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    if (habilitado) Color(0xFF153A60) else Color(0xFF526A82),
                    RoundedCornerShape(9.dp)
                )
                .clickable {
                    if (habilitado) {
                        aberto = !aberto
                    }
                }
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = valor.ifBlank { placeholder },
                color = if (valor.isBlank()) Color(0xFF79B9F2) else Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "↓",
                color = Color(0xFFB7C4D1),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        if (aberto && habilitado) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .background(Color.White)
                    .border(1.dp, Color.Gray)
            ) {
                if (opcoes.isEmpty()) {
                    OpcaoSelecaoUnidadeCineJoy(
                        texto = "Nenhuma opção encontrada",
                        onClick = { aberto = false }
                    )
                } else {
                    opcoes.forEach { opcao ->
                        OpcaoSelecaoUnidadeCineJoy(
                            texto = opcao,
                            onClick = {
                                onSelecionar(opcao)
                                aberto = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpcaoSelecaoUnidadeCineJoy(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(Color.White)
            .border(0.5.dp, Color.LightGray)
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif
        )
    }
}