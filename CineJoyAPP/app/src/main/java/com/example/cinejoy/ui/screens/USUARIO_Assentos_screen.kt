package com.example.cinejoy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R
import com.example.cinejoy.data.model.Assento
import com.example.cinejoy.data.model.Sessao
import com.example.cinejoy.data.model.Unidade

@Composable
fun UsuarioAssentosScreen(
    assentos: List<Assento>,
    selecionados: List<Assento>,
    quantidadeIngressos: Int = 1,
    unidade: Unidade? = null,
    sessao: Sessao? = null,
    mensagem: String = "",
    onAssentoClick: (Assento) -> Unit = {},
    onProximoClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6E7000), Color(0xFFF3F25C))
                )
            )
    ) {
        TopoCineJoyUsuario(onLogoClick = onLogoClick)
        LinhaCronologicaCompra(etapaAtual = "Sessões")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Escolha seu assento",
                color = Color.White,
                fontSize = 26.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Selecione $quantidadeIngressos assento(s). Selecionados: ${selecionados.size}",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            MapaAssentos(
                assentos = assentos,
                selecionados = selecionados,
                onAssentoClick = onAssentoClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            LegendaAssentos()

            if (mensagem.isNotBlank()) {
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            InfoAssento(unidade?.enderecoResumido ?: "Unidade não informada")
            InfoAssento(sessao?.sala ?: "Sala não informada")
            InfoAssento("${sessao?.data ?: "--"} - ${sessao?.horario ?: "--"}")

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BotaoFluxoCineJoy("VOLTAR", onClick = onVoltarClick)
                BotaoFluxoCineJoy("PRÓXIMO", onClick = onProximoClick)
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun MapaAssentos(
    assentos: List<Assento>,
    selecionados: List<Assento>,
    onAssentoClick: (Assento) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18212B), RoundedCornerShape(12.dp))
            .border(1.dp, Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        val linhas = assentos.groupBy { it.linha }.toSortedMap()

        linhas.forEach { (letra, assentosLinha) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = letra,
                    color = Color.White,
                    fontSize = 9.sp,
                    modifier = Modifier.width(20.dp)
                )

                assentosLinha.sortedBy { it.numero }.forEachIndexed { index, assento ->
                    val selecionado = selecionados.any { it.id == assento.id }

                    AssentoBolinha(
                        ocupado = assento.ocupado,
                        selecionado = selecionado,
                        onClick = {
                            if (!assento.ocupado) {
                                onAssentoClick(assento)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    if (index == 7) {
                        Spacer(modifier = Modifier.width(18.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = letra,
                    color = Color.White,
                    fontSize = 9.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(
                    Color(0xFFC8C8C8),
                    RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TELA",
                color = Color.Black,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AssentoBolinha(
    ocupado: Boolean = false,
    selecionado: Boolean = false,
    onClick: () -> Unit = {}
) {
    if (ocupado) {
        Image(
            painter = painterResource(id = R.drawable.indisponivel),
            contentDescription = "Indisponível",
            modifier = Modifier.size(13.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .size(13.dp)
                .background(
                    if (selecionado) Color(0xFFFFF176) else Color(0xFFA6BBFF),
                    CircleShape
                )
                .border(
                    width = if (selecionado) 2.dp else 0.dp,
                    color = if (selecionado) Color(0xFF12395E) else Color.Transparent,
                    shape = CircleShape
                )
                .clickable { onClick() }
        )
    }
}

@Composable
fun LegendaAssentos() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            LegendaDisponivel()
            LegendaImagem("Ocupado", R.drawable.usuario)
            LegendaImagem("Indisponível", R.drawable.indisponivel)
        }

        Column {
            LegendaImagem("Cadeirante", R.drawable.cadeirante)
            LegendaTexto("Acompanhante", "AC")
        }
    }
}

@Composable
fun LegendaDisponivel() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color(0xFF6AA7E8), CircleShape)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "Disponível",
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun LegendaImagem(texto: String, imagem: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Image(
            painter = painterResource(id = imagem),
            contentDescription = texto,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = texto,
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun LegendaTexto(texto: String, sigla: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFF6AA7E8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sigla,
                color = Color(0xFF183651),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = texto,
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun InfoAssento(texto: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(42.dp)
            .background(Color.White)
            .border(1.dp, Color(0xFFC5C600)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            color = Color(0xFF6E7000),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(start = 14.dp)
        )
    }
}