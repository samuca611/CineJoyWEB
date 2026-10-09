package com.example.cinejoy.data.repository

import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Assento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AssentoRepository {
    fun assentosPadrao(): List<Assento> {
        val linhas = listOf("A", "B", "C", "D", "E")

        return linhas.flatMapIndexed { linhaIndex, linha ->
            (1..8).map { numero ->
                Assento(
                    id = linhaIndex * 8 + numero,
                    linha = linha,
                    numero = numero,
                    ocupado = linha == "C" && numero in 4..5,
                    idSala = 1
                )
            }
        }
    }

    suspend fun listarAssentosSala(idSala: Int): List<Assento> = withContext(Dispatchers.IO) {
        val resposta = CineJoyApi.getArray("/assentos/sala/$idSala")

        List(resposta.length()) { index ->
            val item = resposta.getJSONObject(index)
            val codigo = item.optString("numeroCadeira", "A${(index + 1).toString().padStart(2, '0')}")
            val linha = codigo.take(1).ifBlank { "A" }
            val numero = codigo.drop(1).toIntOrNull() ?: (index + 1)

            Assento(
                id = item.optInt("idCadeira", index + 1),
                linha = linha,
                numero = numero,
                ocupado = item.optString("status", "L").trim().equals("O", ignoreCase = true),
                idSala = item.optInt("idSala", idSala)
            )
        }
    }
}
