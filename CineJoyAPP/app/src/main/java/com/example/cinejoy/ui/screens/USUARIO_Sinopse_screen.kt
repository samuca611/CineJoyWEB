package com.example.cinejoy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R
import com.example.cinejoy.data.model.Filme

@Composable
fun UsuarioSinopseScreen(
    filme: Filme?,
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
        LinhaCronologicaCompra(etapaAtual = "Filme")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = filme?.nome ?: "Filme",
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Image(
                painter = painterResource(id = filme?.imagem ?: R.drawable.logo),
                contentDescription = filme?.nome ?: "Filme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(230.dp)
                    .height(330.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFDE0), RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0xFFC5C600), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = filme?.sinopse ?: "Sinopse não disponível",
                    color = Color(0xFF333300),
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            CampoInfoSinopse("Gênero", filme?.genero ?: "--")
            CampoInfoSinopse("Classificação", filme?.classificacao ?: "--")
            CampoInfoSinopse("Duração", filme?.duracao ?: "--")
            CampoInfoSinopse("Idioma", filme?.idioma ?: "--")
            CampoInfoSinopse("Valor", filme?.preco ?: "R$0.00")

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
fun CampoInfoSinopse(titulo: String, valor: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(44.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFC5C600), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$titulo: ",
            color = Color(0xFF6E7000),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Text(
            text = valor,
            color = Color(0xFF333300),
            fontFamily = FontFamily.Serif
        )
    }
}