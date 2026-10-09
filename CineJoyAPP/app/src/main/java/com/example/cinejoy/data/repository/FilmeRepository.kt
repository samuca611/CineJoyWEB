package com.example.cinejoy.data.repository

import com.example.cinejoy.R
import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Filme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FilmeRepository {
    fun listarFilmes(): List<Filme> {
        return listOf(
            Filme(
                id = 1,
                nome = "Barbie",
                imagem = R.drawable.barbie,
                sinopse = "Barbie vive em um mundo perfeito, mas comeca a questionar sua realidade e parte para uma aventura no mundo real.",
                genero = "Comedia / Aventura",
                duracao = "1h54",
                idioma = "Dublado",
                classificacao = "12 anos",
                preco = "R$ 20,00"
            ),
            Filme(
                id = 2,
                nome = "Avatar",
                imagem = R.drawable.avatar,
                sinopse = "Em Pandora, uma nova jornada comeca entre humanos e o povo Na'vi.",
                genero = "Ficcao cientifica",
                duracao = "3h12",
                idioma = "Dublado",
                classificacao = "14 anos",
                preco = "R$ 25,00"
            ),
            Filme(
                id = 3,
                nome = "Mario",
                imagem = R.drawable.mario,
                sinopse = "Mario e Luigi entram em uma aventura para salvar o Reino dos Cogumelos.",
                genero = "Animacao / Aventura",
                duracao = "1h32",
                idioma = "Dublado",
                classificacao = "Livre",
                preco = "R$ 18,00"
            ),
            Filme(
                id = 4,
                nome = "Vingadores",
                imagem = R.drawable.vingadores,
                sinopse = "Herois se unem para enfrentar uma grande ameaca.",
                genero = "Acao / Aventura",
                duracao = "2h23",
                idioma = "Dublado",
                classificacao = "12 anos",
                preco = "R$ 22,00"
            )
        )
    }

    suspend fun listarFilmesApi(): List<Filme> = withContext(Dispatchers.IO) {
        val resposta = CineJoyApi.getArray("/catalogo")

        List(resposta.length()) { index ->
            val item = resposta.getJSONObject(index)
            val nome = item.optString("nome", "Filme")

            Filme(
                id = item.optInt("idFilme", index + 1),
                nome = nome,
                imagem = imagemPorNome(nome),
                sinopse = item.optString("descricao", "Sinopse nao disponivel"),
                genero = item.optString("genero", generoPorNome(nome)),
                duracao = formatarDuracao(item.optString("duracao", "")),
                idioma = item.optString("idioma", "Dublado"),
                classificacao = formatarClassificacao(item.optString("classificacaoIndicativa", "L")),
                preco = "R$ 20,00"
            )
        }
    }

    private fun imagemPorNome(nome: String): Int {
        val filme = nome.lowercase()

        return when {
            "barbie" in filme -> R.drawable.barbie
            "avatar" in filme -> R.drawable.avatar
            "mario" in filme -> R.drawable.mario
            "vingadores" in filme -> R.drawable.vingadores
            else -> R.drawable.filme
        }
    }

    private fun generoPorNome(nome: String): String {
        val filme = nome.lowercase()

        return when {
            "barbie" in filme -> "Comedia / Aventura"
            "avatar" in filme -> "Ficcao cientifica"
            "mario" in filme -> "Animacao / Aventura"
            "vingadores" in filme -> "Acao / Aventura"
            else -> "Cinema"
        }
    }

    private fun formatarDuracao(duracao: String): String {
        val partes = duracao.split(":")
        if (partes.size < 2) {
            return duracao.ifBlank { "Duracao" }
        }

        val horas = partes[0].toIntOrNull() ?: 0
        val minutos = partes[1].toIntOrNull() ?: 0

        return if (horas > 0) "${horas}h${minutos.toString().padStart(2, '0')}" else "${minutos}min"
    }

    private fun formatarClassificacao(classificacao: String): String {
        val valor = classificacao.trim()

        return if (valor.equals("L", ignoreCase = true)) "Livre" else "$valor anos"
    }
}
