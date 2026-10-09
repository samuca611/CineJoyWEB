package com.example.cinejoy.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R


@Composable
fun LoginScreen(
    onCadastrarClick: () -> Unit = {},
    onEntrarClick: (String, String) -> Unit = { _, _ -> },
    onVisitanteClick: () -> Unit = {},
    onFuncionarioClick: (String, String) -> Unit = { _, _ -> },
    mensagem: String = ""
) {


    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF3F668F),
                        Color(0xFF79B9F2)
                    )
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp, start = 28.dp, end = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "CINEJOY",
                color = Color(0xFFFFFF00),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.shadow(4.dp)
            )


            Spacer(modifier = Modifier.height(14.dp))


            Box(
                modifier = Modifier
                    .width(190.dp)
                    .height(190.dp)
                    .shadow(6.dp, RoundedCornerShape(22.dp))
                    .background(Color.White, RoundedCornerShape(22.dp))
                    .border(1.dp, Color(0xFF1F3850), RoundedCornerShape(22.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo CineJoy",
                    modifier = Modifier.size(155.dp)
                )
            }


            Spacer(modifier = Modifier.height(18.dp))


            Text(
                text = "Seja bem-vindo!",
                color = Color(0xFFFFF7B0),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.shadow(3.dp)
            )


            Spacer(modifier = Modifier.height(38.dp))


            CampoLogin(
                valor = login,
                aoMudar = { login = it },
                placeholder = "Login"
            )


            Spacer(modifier = Modifier.height(22.dp))


            CampoLogin(
                valor = senha,
                aoMudar = { senha = it },
                placeholder = "Senha",
                senha = true
            )


            Spacer(modifier = Modifier.height(26.dp))

            if (mensagem.isNotBlank()) {
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(8.dp))
            }


            BotaoLoginPrincipal(
                texto = "Entrar",
                onClick = { onEntrarClick(login, senha) }
            )


            Spacer(modifier = Modifier.height(14.dp))


            BotaoLoginSecundario(
                texto = "Cadastrar-me",
                onClick = onCadastrarClick
            )


            Spacer(modifier = Modifier.height(14.dp))


            BotaoLoginVisitante(
                texto = "Acessar como visitante",
                onClick = onVisitanteClick
            )


            Spacer(modifier = Modifier.height(28.dp))


            Text(
                text = "Sou funcionário",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    onFuncionarioClick(login, senha)
                }
            )
        }
    }
}


@Composable
fun CampoLogin(
    valor: String,
    aoMudar: (String) -> Unit,
    placeholder: String,
    senha: Boolean = false
) {
    TextField(
        value = valor,
        onValueChange = aoMudar,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF79B9F2),
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif
            )
        },
        singleLine = true,
        visualTransformation = if (senha) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .width(245.dp)
            .height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF12395E),
            unfocusedContainerColor = Color(0xFF12395E),
            disabledContainerColor = Color(0xFF12395E),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}


@Composable
fun BotaoLoginPrincipal(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(245.dp)
            .height(48.dp)
            .background(Color(0xFFC7C900), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF7A7A00), RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}


@Composable
fun BotaoLoginSecundario(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(245.dp)
            .height(46.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFC7C900), RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color(0xFF727300),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}


@Composable
fun BotaoLoginVisitante(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(245.dp)
            .height(46.dp)
            .background(Color(0xFF12395E), RoundedCornerShape(8.dp))
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}
