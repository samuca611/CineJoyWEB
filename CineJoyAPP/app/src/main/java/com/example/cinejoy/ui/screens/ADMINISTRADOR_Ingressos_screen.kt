package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ADMINISTRADOR_Ingressos_screen(navController: NavController) {

    val azulEscuro = Color(0xFF12395E)
    val azulMedio = Color(0xFF5B88B5)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    var id by remember { mutableStateOf("") }
    var idSessao by remember { mutableStateOf("") }
    var numAssento by remember { mutableStateOf("") }
    var tipoIngresso by remember { mutableStateOf("") }
    var cpfCliente by remember { mutableStateOf("") }
    var nomeCliente by remember { mutableStateOf("") }
    var idComida by remember { mutableStateOf("") }
    var qtdeComida by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cinzaFundo)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {

        TopoAdminGenerico(
            titulo = "Ingressos",
            azulEscuro = azulEscuro,
            amarelo = amarelo
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoAdminGenerico(
            valor = id,
            aoMudar = { id = it },
            placeholder = "ID",
            cor = amarelo,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(150.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        val campos = listOf(
            Triple("idSessao", idSessao, { v: String -> idSessao = v }),
            Triple("Numero do Assento", numAssento, { v: String -> numAssento = v }),
            Triple("Tipo de Ingresso", tipoIngresso, { v: String -> tipoIngresso = v }),
            Triple("Cpf do Cliente", cpfCliente, { v: String -> cpfCliente = v }),
            Triple("NomeCliente", nomeCliente, { v: String -> nomeCliente = v }),
            Triple("IdComida", idComida, { v: String -> idComida = v }),
            Triple("Qtde de Comida", qtdeComida, { v: String -> qtdeComida = v })
        )

        campos.forEachIndexed { index, (label, valor, onChange) ->
            val corCampo = if (index % 2 == 0) azulEscuro else azulMedio

            CampoAdminGenerico(
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

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .size(42.dp)
                    .background(azulMedio, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "⚙",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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