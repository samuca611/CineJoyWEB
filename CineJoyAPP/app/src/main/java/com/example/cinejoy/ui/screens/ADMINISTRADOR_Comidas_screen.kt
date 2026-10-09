package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class ItemComida(
    val id: Int,
    val nome: String,
    val selecionado: Boolean = false
)

@Composable
fun ADMINISTRADOR_Comidas_screen(navController: NavController) {

    val azulEscuro = Color(0xFF12395E)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    var idSelecionado by remember { mutableStateOf("") }

    val comidas = remember {
        mutableStateListOf(
            ItemComida(1, "Pipoca"),
            ItemComida(2, "Refrigerante"),
            ItemComida(3, "Hot Dog"),
            ItemComida(4, "Combo"),
            ItemComida(5, "Nachos"),
            ItemComida(6, "Suco")
        )
    }

    var comidaSelecionada by remember { mutableStateOf<ItemComida?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cinzaFundo)
    ) {

        TopoAdminGenerico(
            titulo = "Comida",
            azulEscuro = azulEscuro,
            amarelo = amarelo
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = idSelecionado,
            onValueChange = { idSelecionado = it },
            placeholder = { Text("ID", color = Color.DarkGray) },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(150.dp)
                .height(52.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFD0D0D0),
                unfocusedContainerColor = Color(0xFFD0D0D0),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(comidas) { comida ->
                CardComida(
                    comida = comida,
                    selecionado = comidaSelecionada?.id == comida.id,
                    onClick = {
                        comidaSelecionada = comida
                        idSelecionado = comida.id.toString()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        BotoesCrudGenerico(
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
private fun CardComida(
    comida: ItemComida,
    selecionado: Boolean,
    onClick: () -> Unit
) {
    val azulCard = if (selecionado) Color(0xFF12395E) else Color(0xFF5B88B5)
    val borderCor = if (selecionado) Color(0xFFFFD447) else Color.Transparent

    Box(
        modifier = Modifier
            .size(130.dp)
            .background(azulCard, RoundedCornerShape(14.dp))
            .border(2.dp, borderCor, RoundedCornerShape(14.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🍿", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = comida.nome,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}