package com.example.cinejoy.data.repository


import com.example.cinejoy.R
import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Alimento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object AlimentoRepository {


    fun listarAlimentos(): List<Alimento> {
        return listOf(
            Alimento(
                id = 1,
                nome = "Pipoca Grande",
                imagem = R.drawable.pipoca,
                preco = 18.00,
                descricao = "Pipoca grande tradicional",
                categoria = "Pipocas"
            ),
            Alimento(
                id = 2,
                nome = "Refrigerante",
                imagem = R.drawable.pipoca,
                preco = 10.00,
                descricao = "Refrigerante gelado",
                categoria = "Bebidas"
            ),
            Alimento(
                id = 3,
                nome = "Combo CineJoy",
                imagem = R.drawable.pipoca,
                preco = 30.00,
                descricao = "Pipoca + refrigerante",
                categoria = "Combos"
            )
        )
    }

    suspend fun listarAlimentosApi(): List<Alimento> = withContext(Dispatchers.IO) {
        val resposta = CineJoyApi.getArray("/produtos")

        List(resposta.length()) { index ->
            val item = resposta.getJSONObject(index)

            Alimento(
                id = item.optInt("idProd", index + 1),
                nome = item.optString("nome", "Produto"),
                imagem = imagemPorCategoria(item.optString("categoria", "")),
                preco = item.optDouble("preco", 0.0),
                descricao = item.optString("descricao", ""),
                categoria = categoriaVisual(item.optString("categoria", ""), item.optString("nome", ""))
            )
        }
    }

    private fun categoriaVisual(categoria: String, nome: String): String {
        val base = "${categoria} ${nome}".lowercase()

        return when {
            "combo" in base -> "Combos"
            "pipoca" in base -> "Pipocas"
            "bebida" in base || "refrigerante" in base || "suco" in base -> "Bebidas"
            "doce" in base || "chocolate" in base || "bala" in base -> "Doces"
            else -> categoria.ifBlank { "Outros" }
        }
    }

    private fun imagemPorCategoria(categoria: String): Int {
        val valor = categoria.lowercase()

        return when {
            "bebida" in valor -> R.drawable.pipoca
            "comida" in valor -> R.drawable.pipoca
            else -> R.drawable.pipoca
        }
    }
}