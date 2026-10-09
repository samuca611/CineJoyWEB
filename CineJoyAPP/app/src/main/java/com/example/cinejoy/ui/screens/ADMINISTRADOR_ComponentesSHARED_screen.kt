package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopoAdminGenerico(
    titulo:     String,
    azulEscuro: Color,
    amarelo:    Color
) {
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
            text       = titulo,
            fontSize   = 26.sp,
            fontWeight = FontWeight.Bold,
            color      = amarelo
        )
    }
}


@Composable
fun CampoAdminGenerico(
    valor:       String,
    aoMudar:     (String) -> Unit,
    placeholder: String,
    cor:         Color,
    modifier:    Modifier = Modifier
) {
    OutlinedTextField(
        value         = valor,
        onValueChange = aoMudar,
        placeholder   = {
            Text(
                text      = placeholder,
                color     = Color.White,
                fontSize  = 14.sp
            )
        },
        modifier  = modifier.height(52.dp),
        singleLine = true,
        shape      = RoundedCornerShape(8.dp),
        colors     = OutlinedTextFieldDefaults.colors(
            focusedContainerColor   = cor,
            unfocusedContainerColor = cor,
            focusedBorderColor      = Color.Transparent,
            unfocusedBorderColor    = Color.Transparent,
            focusedTextColor        = Color.White,
            unfocusedTextColor      = Color.White,
            cursorColor             = Color.White
        )
    )
}

@Composable
fun BotoesCrudGenerico(
    onCadastrar: () -> Unit,
    onAlterar:   () -> Unit,
    onExcluir:   () -> Unit,
    onBuscar:    () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Linha 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BotaoAdminAcao(
                texto    = "Cadastrar",
                cor      = Color(0xFF5B88B5),
                modifier = Modifier.weight(1f),
                onClick  = onCadastrar
            )
            BotaoAdminAcao(
                texto    = "Alterar",
                cor      = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f),
                onClick  = onAlterar
            )
        }
        // Linha 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BotaoAdminAcao(
                texto    = "Excluir",
                cor      = Color(0xFFF44336),
                modifier = Modifier.weight(1f),
                onClick  = onExcluir
            )
            BotaoAdminAcao(
                texto    = "Buscar",
                cor      = Color(0xFFFFD447),
                modifier = Modifier.weight(1f),
                onClick  = onBuscar
            )
        }
    }
}

@Composable
private fun BotaoAdminAcao(
    texto:    String,
    cor:      Color,
    modifier: Modifier = Modifier,
    onClick:  () -> Unit
) {
    Button(
        onClick  = onClick,
        modifier = modifier.height(48.dp),
        shape    = RoundedCornerShape(24.dp),
        colors   = ButtonDefaults.buttonColors(
            containerColor = cor,
            contentColor   = Color.White
        )
    ) {
        Text(
            text       = texto,
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}