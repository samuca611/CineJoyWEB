package com.example.cinejoy.ui.screens.usuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UsuarioValidacaoScreen() {
    val azul = Color(0xFF12395E)
    val amarelo = Color(0xFFFFD447)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7298C7))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(150.dp),
            shape = CircleShape,
            color = amarelo
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("✓", fontSize = 80.sp, color = azul)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Pagamento Validado!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Seu ingresso foi confirmado com sucesso.",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}