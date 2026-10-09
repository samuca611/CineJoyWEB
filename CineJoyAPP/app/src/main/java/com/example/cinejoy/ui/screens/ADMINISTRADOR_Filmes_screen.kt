package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ADMINISTRADOR_Filmes_screen(navController: NavController) {

    val azulEscuro = Color(0xFF12395E)
    val azulMedio = Color(0xFF5B88B5)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    var id by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var classificacao by remember { mutableStateOf("") }
    var sinopse by remember { mutableStateOf("") }
    var dataEstreia by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf("") }
    var direcao by remember { mutableStateOf("") }
    var idioma by remember { mutableStateOf("") }
    var dimensaoVisual by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cinzaFundo)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {

        TopoAdminFilmes(azulEscuro, amarelo)

        Spacer(modifier = Modifier.height(16.dp))

        CampoAdminFilmes(
            valor = id,
            aoMudar = { id = it },
            placeholder = "ID",
            cor = amarelo,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(150.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        listOf(
            Triple("Título", titulo, { v: String -> titulo = v }),
            Triple("Gênero", genero, { v: String -> genero = v }),
            Triple("Classificação Indicativa", classificacao, { v: String -> classificacao = v }),
            Triple("Sinopse", sinopse, { v: String -> sinopse = v }),
            Triple("Data Estreia", dataEstreia, { v: String -> dataEstreia = v }),
            Triple("Data de estreia", dataEstreia, { v: String -> dataEstreia = v }),
            Triple("Duração", duracao, { v: String -> duracao = v }),
            Triple("Direção", direcao, { v: String -> direcao = v }),
            Triple("Idioma", idioma, { v: String -> idioma = v }),
            Triple("Dimensão visual (2D, 3D...)", dimensaoVisual, { v: String -> dimensaoVisual = v }),
            Triple("Status (em cartaz, em breve...)", status, { v: String -> status = v })
        ).forEachIndexed { index, (label, valor, onChange) ->

            val corCampo = if (index % 2 == 0) azulEscuro else azulMedio

            CampoAdminFilmes(
                valor = valor,
                aoMudar = onChange,
                placeholder = label,
                cor = corCampo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        BotoesCrudFilmes(
            azulEscuro = azulEscuro,
            onCadastrar = {

            },
            onAlterar = {

            },
            onExcluir = {

            },
            onBuscar = {

            }
        )
    }
}

@Composable
private fun TopoAdminFilmes(azulEscuro: Color, amarelo: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(azulEscuro)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(amarelo, RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Filmes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = amarelo
        )
    }
}

@Composable
private fun CampoAdminFilmes(
    valor: String,
    aoMudar: (String) -> Unit,
    placeholder: String,
    cor: Color,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = aoMudar,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.White,
                fontSize = 14.sp
            )
        },
        modifier = modifier.height(52.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = cor,
            unfocusedContainerColor = cor,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        )
    )
}

@Composable
private fun BotoesCrudFilmes(
    azulEscuro: Color,
    onCadastrar: () -> Unit,
    onAlterar: () -> Unit,
    onExcluir: () -> Unit,
    onBuscar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BotaoAcao(
                texto = "Cadastrar",
                cor = Color(0xFF5B88B5),
                modifier = Modifier.weight(1f),
                onClick = onCadastrar
            )

            BotaoAcao(
                texto = "Alterar",
                cor = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f),
                onClick = onAlterar
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BotaoAcao(
                texto = "Excluir",
                cor = Color(0xFFF44336),
                modifier = Modifier.weight(1f),
                onClick = onExcluir
            )

            BotaoAcao(
                texto = "Buscar",
                cor = Color(0xFFFFD447),
                modifier = Modifier.weight(1f),
                onClick = onBuscar
            )
        }
    }
}

@Composable
private fun BotaoAcao(
    texto: String,
    cor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = cor,
            contentColor = Color.White
        )
    ) {
        Text(
            text = texto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}