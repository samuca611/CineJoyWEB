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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun UsuarioProgramacaoScreen(
    filmes: List<Filme>,
    onSinopseClick: (Filme) -> Unit = {},
    onSessoesClick: (Filme) -> Unit = {},
    onLogoClick: () -> Unit = {},
    onUsuarioClick: () -> Unit = {}
) {
    var pesquisa by remember { mutableStateOf("") }

    val filmesFiltrados = remember(filmes, pesquisa) {
        if (pesquisa.isBlank()) {
            filmes
        } else {
            filmes.filter { it.nome.contains(pesquisa, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B2B2B))
    ) {
        TopBar(
            onLogoClick = onLogoClick,
            onUsuarioClick = onUsuarioClick
        )

        CampoPesquisaFilmes(
            valor = pesquisa,
            onValorChange = { pesquisa = it }
        )

        if (filmesFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum filme encontrado.",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filmesFiltrados) { filme ->
                    FilmeItem(
                        filme = filme,
                        onSinopseClick = { onSinopseClick(filme) },
                        onSessoesClick = { onSessoesClick(filme) }
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onLogoClick: () -> Unit = {},
    onUsuarioClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF12395E))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Menu CineJoy",
            modifier = Modifier
                .size(52.dp)
                .clickable { onLogoClick() }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "CINEJOY",
            color = Color(0xFFC7C900),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.usuario),
            contentDescription = "Minha conta",
            modifier = Modifier
                .size(48.dp)
                .clickable { onUsuarioClick() }
        )
    }
}

@Composable
fun CampoPesquisaFilmes(
    valor: String,
    onValorChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF12395E))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .background(Color(0xFF79B9F2), RoundedCornerShape(18.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (valor.isBlank()) {
                Text(
                    text = "🔍  Pesquisar filmes",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            BasicTextField(
                value = valor,
                onValueChange = onValorChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            )
        }
    }
}

@Composable
fun FilmeItem(
    filme: Filme,
    onSinopseClick: () -> Unit = {},
    onSessoesClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(205.dp)
            .background(Color(0xFF363832), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF6AA7E8), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = filme.imagem),
            contentDescription = filme.nome,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(125.dp)
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = filme.nome,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = filme.genero,
                color = Color(0xFF9EC1E6),
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "${filme.duracao} • ${filme.classificacao}",
                color = Color.White,
                fontSize = 13.sp,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.weight(1f))

            BotaoFilme("Ver sinopse", onClick = onSinopseClick)

            Spacer(modifier = Modifier.height(8.dp))

            BotaoFilme("Sessões", onClick = onSessoesClick)
        }
    }
}

@Composable
fun BotaoFilme(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(135.dp)
            .height(34.dp)
            .background(Color(0xFF12395E), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF6AA7E8), RoundedCornerShape(20.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 13.sp,
            fontFamily = FontFamily.Serif
        )
    }
}