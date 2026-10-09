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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R

object UsuarioTopoActions {
    var abrirConta: (() -> Unit)? = null
}

@Composable
fun TopoCineJoyUsuario(
    onLogoClick: () -> Unit = {},
    onUsuarioClick: (() -> Unit)? = null,
    mostrarUsuario: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(Color(0xFF727300))
            .border(1.dp, Color.White)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Menu CineJoy",
            modifier = Modifier
                .size(55.dp)
                .clickable { onLogoClick() }
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = "CINEJOY",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = FontFamily.Cursive
        )

        Spacer(modifier = Modifier.weight(1f))

        if (mostrarUsuario) {
            Image(
                painter = painterResource(id = R.drawable.usuario),
                contentDescription = "Minha conta",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val acao = onUsuarioClick ?: UsuarioTopoActions.abrirConta
                        acao?.invoke()
                    }
            )
        }
    }
}

@Composable
fun LinhaCronologicaCompra(
    etapaAtual: String
) {
    val etapas = listOf("Filme", "Sessões", "Alimentos", "Pagamento")
    val indiceAtual = etapas.indexOf(etapaAtual).coerceAtLeast(0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        etapas.forEachIndexed { index, etapa ->
            EtapaCronologica(
                texto = etapa,
                ativo = index <= indiceAtual,
                selecionado = index == indiceAtual
            )

            if (index < etapas.lastIndex) {
                Spacer(modifier = Modifier.width(18.dp))
            }
        }
    }
}

@Composable
private fun EtapaCronologica(
    texto: String,
    ativo: Boolean,
    selecionado: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 11.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .size(if (selecionado) 38.dp else 34.dp)
                .background(
                    if (ativo) Color.White else Color(0xFFBDBD7B),
                    CircleShape
                )
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (texto) {
                    "Filme" -> "F"
                    "Sessões" -> "S"
                    "Alimentos" -> "A"
                    else -> "P"
                },
                color = Color(0xFF333300),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BotaoFluxoCineJoy(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .width(120.dp)
            .height(42.dp)
            .background(Color(0xFFC5C600), RoundedCornerShape(22.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}