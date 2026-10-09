package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun ADMINISTRADOR_Usuarios_screen(navController: NavController) {

    val azulEscuro = Color(0xFF12395E)
    val azulMedio = Color(0xFF5B88B5)
    val cinzaFundo = Color(0xFF8D8D8D)
    val amarelo = Color(0xFFFFD447)

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    var telefone by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    var mostrarMais by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cinzaFundo)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopoAdminGenerico(
            titulo = "Usuario",
            azulEscuro = azulEscuro,
            amarelo = amarelo
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(azulEscuro, RoundedCornerShape(12.dp))
                .border(2.dp, Color(0xFFB0B0B0), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CampoAdminGenerico(
            valor = nome,
            aoMudar = { nome = it },
            placeholder = "Nome: Samuel Davi",
            cor = azulMedio,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoAdminGenerico(
            valor = email,
            aoMudar = { email = it },
            placeholder = "Email: samu123@gmail.com",
            cor = azulEscuro,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoAdminGenerico(
            valor = cpf,
            aoMudar = { cpf = it },
            placeholder = "Cpf: 31472838234",
            cor = azulMedio,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { mostrarMais = !mostrarMais },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
                .height(38.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = azulMedio,
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (mostrarMais) "mostrar menos" else "mostrar mais",
                fontSize = 13.sp
            )
        }

        if (mostrarMais) {
            Spacer(modifier = Modifier.height(10.dp))

            listOf(
                Triple("Telefone", telefone, { v: String -> telefone = v }),
                Triple("Data Nascimento", dataNascimento, { v: String -> dataNascimento = v }),
                Triple("Cidade", cidade, { v: String -> cidade = v }),
                Triple("Estado", estado, { v: String -> estado = v })
            ).forEachIndexed { index, (label, valor, onChange) ->
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
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Alterar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD447),
                    contentColor = Color(0xFF12395E)
                )
            ) {
                Text(
                    text = "Buscar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}