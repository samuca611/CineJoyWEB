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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R

@Composable
fun UsuarioFormaPagamentoScreen(
    usuarioCadastrado: Boolean,
    precoIngresso: String = "R$0.00",
    quantidadeIngressos: Int = 1,
    totalCompra: Double = 0.0,
    mensagem: String = "",
    onCadastrarNecessario: () -> Unit = {},
    onFinalizarClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6E7000), Color(0xFFF3F25C))
                )
            )
    ) {
        TopoCineJoyUsuario(onLogoClick = onLogoClick)
        LinhaCronologicaCompra(etapaAtual = "Pagamento")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Confirme seu ingresso",
                color = Color.White,
                fontSize = 23.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5D6100), RoundedCornerShape(12.dp))
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TipoIngresso("Inteira", precoIngresso, selecionado = true)
                TipoIngresso("Quantidade", quantidadeIngressos.toString())
                TipoIngresso("Total", "R$ ${"%.2f".format(totalCompra)}")
            }

            if (mensagem.isNotBlank()) {
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Formas de Pagamento",
                color = Color(0xFF6E7000),
                fontSize = 21.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FormaPagamento(R.drawable.boleto, "BOLETO")
                FormaPagamento(R.drawable.pix, "PIX")
                FormaPagamento(R.drawable.cartao, "CARTÃO")
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (!usuarioCadastrado) {
                Text(
                    text = "Para finalizar a compra, faça cadastro ou entre em uma conta.",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BotaoFluxoCineJoy("VOLTAR", onClick = onVoltarClick)

                BotaoFluxoCineJoy(
                    texto = if (usuarioCadastrado) "FINALIZAR" else "CADASTRAR",
                    onClick = {
                        if (usuarioCadastrado) {
                            onFinalizarClick()
                        } else {
                            onCadastrarNecessario()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun TipoIngresso(
    nome: String,
    preco: String,
    selecionado: Boolean = false
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(245.dp)
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(
                width = if (selecionado) 2.dp else 0.dp,
                color = if (selecionado) Color(0xFF00A6FF) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(
                    if (selecionado) Color(0xFF00A6FF) else Color.LightGray,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = nome,
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = preco,
            color = Color(0xFFC5C600),
            fontSize = 17.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FormaPagamento(imagem: Int, texto: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .background(Color(0xFFC5C600), RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFF333300), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imagem),
                contentDescription = texto,
                modifier = Modifier.size(58.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = texto,
            color = Color(0xFF6E7000),
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif
        )
    }
}