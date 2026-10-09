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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R
import com.example.cinejoy.data.model.Filme
import com.example.cinejoy.data.model.Sessao
import com.example.cinejoy.data.model.Unidade

@Composable
fun UsuarioSessaoScreen(
    filme: Filme?,
    unidadeSelecionada: Unidade?,
    quantidadeIngressos: Int,
    sessoes: List<Sessao>,
    sessaoSelecionada: Sessao?,
    mensagem: String = "",
    onEscolherUnidadeClick: () -> Unit = {},
    onQuantidadeChange: (Int) -> Unit = {},
    onSessaoClick: (Sessao) -> Unit,
    onProximoClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    val datasDisponiveis = sessoes.map { it.data }.distinct()
    var dataSelecionada by remember(sessoes) { mutableStateOf(datasDisponiveis.firstOrNull().orEmpty()) }

    val sessoesFiltradas = if (dataSelecionada.isBlank()) {
        sessoes
    } else {
        sessoes.filter { it.data == dataSelecionada }
    }

    val valorBase = filme?.preco
        ?.replace("R$", "")
        ?.replace(",", ".")
        ?.trim()
        ?.toDoubleOrNull() ?: 0.0

    val totalIngressos = valorBase * quantidadeIngressos

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
                .padding(horizontal = 22.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Escolha sua sessão",
                color = Color.White,
                fontSize = 27.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = filme?.imagem ?: R.drawable.logo),
                    contentDescription = filme?.nome ?: "Filme",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(128.dp)
                        .height(184.dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = filme?.nome ?: "Filme",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InfoFilmeSessao(filme?.idioma ?: "Idioma")
                    InfoFilmeSessao(filme?.duracao ?: "Duração")
                    InfoFilmeSessao(filme?.classificacao ?: "Classificação")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = filme?.preco ?: "R$0.00",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(Color(0xFF6AA7E8), RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0xFF183651), RoundedCornerShape(14.dp))
                    .clickable { onEscolherUnidadeClick() }
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = unidadeSelecionada?.let {
                        "${it.nome}\n${it.cidade} - ${it.siglaEstado}"
                    } ?: "Escolha sua Unidade CineJoy",
                    color = Color.White,
                    fontSize = if (unidadeSelecionada == null) 16.sp else 14.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            ControleQuantidadeIngressos(
                quantidade = quantidadeIngressos,
                total = totalIngressos,
                onQuantidadeChange = onQuantidadeChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (unidadeSelecionada == null) {
                AvisoSessao("Escolha estado, cidade e unidade para carregar horários e salas.")
            } else {
                BarraDatasSessao(
                    datasDisponiveis = datasDisponiveis,
                    dataSelecionada = dataSelecionada,
                    onDataSelecionada = { dataSelecionada = it }
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (sessoes.isEmpty()) {
                    AvisoSessao("Nenhuma sessão cadastrada para este filme nessa unidade.")
                }

                sessoesFiltradas.forEach { sessao ->
                    CardSessao(
                        horario = sessao.horario,
                        sala = "${sessao.sala} • ${sessao.idioma} • ${sessao.formato}",
                        data = sessao.data,
                        selecionado = sessaoSelecionada?.id == sessao.id,
                        onClick = { onSessaoClick(sessao) }
                    )
                }
            }

            if (mensagem.isNotBlank()) {
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

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
fun ControleQuantidadeIngressos(
    quantidade: Int,
    total: Double,
    onQuantidadeChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFC5C600), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Quantidade de ingressos",
                color = Color(0xFF333300),
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Total: R$ ${"%.2f".format(total)}",
                color = Color(0xFF6E7000),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }

        BotaoQuantidadeSessao("-") { onQuantidadeChange(quantidade - 1) }

        Text(
            text = quantidade.toString(),
            color = Color(0xFF183651),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        BotaoQuantidadeSessao("+") { onQuantidadeChange(quantidade + 1) }
    }
}

@Composable
fun BotaoQuantidadeSessao(
    texto: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color(0xFF6AA7E8), CircleShape)
            .border(1.dp, Color(0xFF183651), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color(0xFF183651),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BarraDatasSessao(
    datasDisponiveis: List<String>,
    dataSelecionada: String,
    onDataSelecionada: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White, RoundedCornerShape(14.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (datasDisponiveis.isEmpty()) {
            Text(
                text = "Sem datas disponíveis",
                color = Color(0xFF183651),
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )
        } else {
            datasDisponiveis.take(4).forEach { data ->
                DataSessao(
                    texto = data,
                    ativo = data == dataSelecionada,
                    onClick = { onDataSelecionada(data) }
                )
            }
        }
    }
}

@Composable
fun AvisoSessao(texto: String) {
    Text(
        text = texto,
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
    )
}

@Composable
fun CardSessao(
    horario: String,
    sala: String,
    data: String,
    selecionado: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .height(76.dp)
            .background(
                if (selecionado) Color(0xFF3D8EE8) else Color(0xFF6AA7E8),
                RoundedCornerShape(5.dp)
            )
            .border(
                width = if (selecionado) 2.dp else 0.dp,
                color = if (selecionado) Color.White else Color.Transparent,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = horario,
            color = Color(0xFF183651),
            fontSize = 26.sp,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = sala,
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.White)
            )

            Text(
                text = data,
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
fun InfoFilmeSessao(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(bottom = 5.dp)
            .border(1.dp, Color(0xFF6AA7E8))
            .background(Color(0xFF183651)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun DataSessao(
    texto: String,
    ativo: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .width(58.dp)
            .height(48.dp)
            .background(
                if (ativo) Color(0xFF6AA7E8) else Color(0xFFF5F5F5),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color(0xFF183651),
            fontSize = 17.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )
    }
}