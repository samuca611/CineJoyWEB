package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.data.model.Usuario

@Composable
fun UsuarioContaScreen(
    usuario: Usuario?,
    visitante: Boolean = false,
    onVoltarClick: () -> Unit = {},
    onCadastrarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF12395E), Color(0xFF2F7EC7))
                )
            )
    ) {
        TopoCineJoyUsuario(
            onLogoClick = onLogoClick,
            onUsuarioClick = {}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Minha conta",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (usuario == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .border(2.dp, Color(0xFFC5C600), RoundedCornerShape(14.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (visitante) "Você está usando o app como visitante." else "Nenhum usuário logado.",
                            color = Color(0xFF12395E),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Cadastre-se ou entre em uma conta para visualizar suas informações pessoais e finalizar compras.",
                            color = Color.DarkGray,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                BotaoContaUsuario(
                    texto = "CADASTRAR / ENTRAR",
                    onClick = onCadastrarClick
                )
            } else {
                CardInfoConta(
                    titulo = "Dados pessoais",
                    campos = listOf(
                        "Nome" to usuario.nome,
                        "CPF" to usuario.cpf,
                        "Data de nascimento" to usuario.dataNascimento
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                CardInfoConta(
                    titulo = "Contato",
                    campos = listOf(
                        "E-mail" to usuario.email,
                        "Telefone" to usuario.telefone
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                CardInfoConta(
                    titulo = "Localização",
                    campos = listOf(
                        "Estado" to usuario.estado.toString(),
                        "Cidade" to usuario.cidade.toString()
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                CardInfoConta(
                    titulo = "Login",
                    campos = listOf(
                        "Usuário" to usuario.userLogin
                    )
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            BotaoContaUsuario(
                texto = "VOLTAR",
                onClick = onVoltarClick
            )
        }
    }
}

@Composable
fun CardInfoConta(
    titulo: String,
    campos: List<Pair<String, String>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(14.dp))
            .border(2.dp, Color(0xFFC5C600), RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xFF12395E),
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(10.dp))

        campos.forEach { campo ->
            CampoInfoConta(
                titulo = campo.first,
                valor = campo.second.ifBlank { "--" }
            )
        }
    }
}

@Composable
fun CampoInfoConta(
    titulo: String,
    valor: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color(0xFFEAF5FF), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2F7EC7), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xFF2F7EC7),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = valor,
            color = Color(0xFF12395E),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun BotaoContaUsuario(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color(0xFFC5C600), RoundedCornerShape(22.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}