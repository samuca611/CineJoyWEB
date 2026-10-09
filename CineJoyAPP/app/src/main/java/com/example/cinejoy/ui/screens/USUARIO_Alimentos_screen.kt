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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cinejoy.data.model.Alimento

@Composable
fun UsuarioAlimentosScreen(
    alimentos: List<Alimento>,
    onFinalizarClick: (List<Alimento>) -> Unit = {},
    onVoltarClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    var quantidades by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    var pesquisa by remember { mutableStateOf("") }

    val categorias = listOf("Todos", "Combos", "Pipocas", "Bebidas", "Doces")

    val alimentosFiltrados = alimentos.filter { alimento ->
        val bateCategoria = categoriaSelecionada == "Todos" ||
                alimento.categoria.equals(categoriaSelecionada, ignoreCase = true)

        val batePesquisa = pesquisa.isBlank() ||
                alimento.nome.contains(pesquisa, ignoreCase = true)

        bateCategoria && batePesquisa
    }

    val total = alimentos.sumOf { alimento ->
        (quantidades[alimento.id] ?: 0) * alimento.preco
    }

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
        LinhaCronologicaCompra(etapaAtual = "Alimentos")

        CampoPesquisaAlimentos(
            valor = pesquisa,
            onValorChange = { pesquisa = it }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categorias.forEach { categoria ->
                CategoriaAlimento(
                    texto = categoria,
                    selecionado = categoria == categoriaSelecionada,
                    onClick = { categoriaSelecionada = categoria }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            if (alimentosFiltrados.isEmpty()) {
                item {
                    Text(
                        text = "Nenhum alimento encontrado.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(alimentosFiltrados) { alimento ->
                CardAlimento(
                    alimento = alimento,
                    quantidade = quantidades[alimento.id] ?: 0,
                    onMaisClick = {
                        val atual = quantidades[alimento.id] ?: 0
                        quantidades = quantidades + (alimento.id to (atual + 1))
                    },
                    onMenosClick = {
                        val atual = quantidades[alimento.id] ?: 0

                        quantidades = if (atual <= 1) {
                            quantidades - alimento.id
                        } else {
                            quantidades + (alimento.id to (atual - 1))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFDE0))
                .border(1.dp, Color(0xFFC5C600))
                .padding(16.dp)
        ) {
            Text(
                text = "Total alimentos: R$ ${"%.2f".format(total)}",
                color = Color(0xFF6E7000),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BotaoFluxoCineJoy("VOLTAR", onClick = onVoltarClick)

                BotaoFluxoCineJoy(
                    texto = if (total == 0.0) "CONTINUAR" else "PRÓXIMO",
                    onClick = {
                        val selecionados = alimentos.flatMap { alimento ->
                            List(quantidades[alimento.id] ?: 0) { alimento }
                        }

                        onFinalizarClick(selecionados)
                    }
                )
            }
        }
    }
}

@Composable
fun CampoPesquisaAlimentos(
    valor: String,
    onValorChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(38.dp)
            .background(Color(0xFF79B9F2), RoundedCornerShape(18.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (valor.isBlank()) {
            Text(
                text = "🔍  Pesquisar alimentos",
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )
        }

        BasicTextField(
            value = valor,
            onValueChange = onValorChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif
            )
        )
    }
}

@Composable
fun CategoriaAlimento(
    texto: String,
    selecionado: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(66.dp)
            .height(28.dp)
            .background(
                if (selecionado) Color(0xFF6AA7E8) else Color(0xFFEAEA6B),
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color(0xFF727300), RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = if (selecionado) Color.White else Color.Black,
            fontSize = 12.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardAlimento(
    alimento: Alimento,
    quantidade: Int = 0,
    onMaisClick: () -> Unit = {},
    onMenosClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp)
            .background(
                if (quantidade > 0) Color(0xFFE8F6FF) else Color(0xFFFFFDB0),
                RoundedCornerShape(6.dp)
            )
            .border(
                width = if (quantidade > 0) 2.dp else 1.dp,
                color = if (quantidade > 0) Color(0xFF4B8ED8) else Color(0xFFC5C600),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = alimento.imagem),
            contentDescription = alimento.nome,
            modifier = Modifier
                .width(92.dp)
                .height(92.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = alimento.nome,
                color = Color(0xFF4B8ED8),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )

            Text(
                text = alimento.descricao,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "R$${"%.2f".format(alimento.preco)}",
                color = Color(0xFFC7C900),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            BotaoQuantidade("-", onClick = onMenosClick)

            Text(
                text = quantidade.toString(),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 7.dp)
            )

            BotaoQuantidade("+", onClick = onMaisClick)
        }
    }
}

@Composable
fun BotaoQuantidade(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(29.dp)
            .background(Color(0xFFF3F25C), CircleShape)
            .border(1.dp, Color(0xFF727300), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}