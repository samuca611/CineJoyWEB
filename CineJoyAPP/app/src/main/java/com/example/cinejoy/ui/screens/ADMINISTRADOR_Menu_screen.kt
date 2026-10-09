package com.example.cinejoy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinejoy.R
import com.example.cinejoy.ui.navigation.Routes


@Composable
fun ADMINISTRADOR_Menu_screen(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF9C9D98))
    ) {
        MenuLateralAdministrador()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 6.dp, top = 15.dp, end = 8.dp)
        ) {
            ItemMenuAdministrador(
                texto = "Funcionário",
                imagem = R.drawable.funcionario,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_CONTA)
                }
            )

            ItemMenuAdministrador(
                texto = "Filme",
                imagem = R.drawable.filme,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_FILMES)
                }
            )

            ItemMenuAdministrador(
                texto = "Comida",
                imagem = R.drawable.pipoca,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_COMIDAS)
                }
            )

            ItemMenuAdministrador(
                texto = "Relatórios",
                imagem = R.drawable.relatorio,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_RELATORIO)
                }
            )

            ItemMenuAdministrador(
                texto = "Usuário",
                imagem = R.drawable.usuario,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_USUARIOS)
                }
            )

            ItemMenuAdministrador(
                texto = "Ingressos",
                imagem = R.drawable.ingresso,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_INGRESSOS)
                }
            )

            ItemMenuAdministrador(
                texto = "Unidade CineJoy",
                imagem = R.drawable.localizacao,
                onClick = {
                    navController.navigate(Routes.ADMINISTRADOR_UNIDADES)
                }
            )
        }
    }
}

@Composable
fun MenuLateralAdministrador() {
    Box(
        modifier = Modifier
            .width(46.dp)
            .fillMaxHeight()
            .background(Color(0xFFD9D9D9))
    ) {
        Divider(
            color = Color(0xFF0B2F57),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(2.dp)
        )

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(36.dp)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(Color(0xFF0B2F57)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "=",
                color = Color.White,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
fun ItemMenuAdministrador(
    texto: String,
    imagem: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF6DA6E8))
            .border(
                width = 1.dp,
                color = Color(0xFF0B2F57),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource(id = imagem),
            contentDescription = texto,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)
                .padding(start = 18.dp)
                .clip(RoundedCornerShape(7.dp))
        )

        Text(
            text = texto,
            color = Color(0xFF142F4B),
            fontSize = 23.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 104.dp)
        )
    }
}