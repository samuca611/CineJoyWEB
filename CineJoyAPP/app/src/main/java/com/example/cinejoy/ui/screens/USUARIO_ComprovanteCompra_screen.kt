package com.example.cinejoy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.R
import com.example.cinejoy.viewmodel.CompraViewModel

@Composable
fun UsuarioComprovanteCompraScreen(
    compraViewModel: CompraViewModel,
    onVoltarInicioClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    val filme = compraViewModel.filmeSelecionado
    val sessao = compraViewModel.sessaoSelecionada
    val assentos = compraViewModel.assentosSelecionados.joinToString(", ") { it.codigo }
    val total = compraViewModel.calcularTotal()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6E7000), Color(0xFFF3F25C))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopoCineJoyUsuario(onLogoClick = onLogoClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.verificacao),
                contentDescription = "Compra aprovada",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Transação aprovada!",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(18.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Comprovante de compra",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Filme: ${filme?.nome ?: "Não informado"}", fontSize = 16.sp)
                    Text("Unidade: ${compraViewModel.unidadeSelecionada?.enderecoResumido ?: "--"}", fontSize = 16.sp)
                    Text("Sessão: ${sessao?.data ?: "--"} - ${sessao?.horario ?: "--"}", fontSize = 16.sp)
                    Text("Sala: ${sessao?.sala ?: "--"}", fontSize = 16.sp)
                    Text("Ingressos: ${compraViewModel.quantidadeIngressos}", fontSize = 16.sp)
                    Text("Assentos: ${assentos.ifBlank { "--" }}", fontSize = 16.sp)

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Total: R$ ${"%.2f".format(total)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6E7000)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp)
                    .background(Color(0xFFC5C600), RoundedCornerShape(22.dp))
                    .clickable { onVoltarInicioClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VOLTAR AO INÍCIO",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}