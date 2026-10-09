package com.example.cinejoy.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R
import com.example.cinejoy.data.model.Filme


@Composable
fun UsuarioMenuScreen(
    filmes: List<Filme>,
    onProgramacaoClick: () -> Unit = {},
    onUnidadesClick: () -> Unit = {},
    onAlimentosClick: () -> Unit = {},
    onCadastroClick: () -> Unit = {},
    onFilmeClick: (Filme) -> Unit = {},
    onSessaoFilmeClick: (Filme) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        UsuarioProgramacaoScreen(
            filmes = filmes,
            onSinopseClick = onFilmeClick,
            onSessoesClick = onSessaoFilmeClick,
            onLogoClick = {}
        )

        Column(
            modifier = Modifier
                .width(176.dp)
                .fillMaxHeight()
                .background(Color(0xFFF0F15B))
                .padding(top = 22.dp, start = 12.dp, end = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo CineJoy",
                    modifier = Modifier.size(43.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "CINEJOY",
                    color = Color(0xFFBFC100),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotaoMenuUsuario(
                texto = "Minha conta",
                onClick = onCadastroClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            BotaoMenuUsuario(
                texto = "Unidades\nCineJoy",
                onClick = onUnidadesClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            BotaoMenuUsuario(
                texto = "Alimentos",
                onClick = onAlimentosClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            BotaoMenuUsuario(
                texto = "Filmes",
                onClick = onProgramacaoClick
            )
        }
    }
}

@Composable
fun BotaoMenuUsuario(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(152.dp)
            .height(47.dp)
            .background(Color(0xFFC7C900), RoundedCornerShape(7.dp))
            .border(1.dp, Color(0xFF3D3D00), RoundedCornerShape(7.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )
    }
}