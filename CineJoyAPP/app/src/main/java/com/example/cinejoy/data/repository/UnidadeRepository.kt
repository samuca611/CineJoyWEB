package com.example.cinejoy.data.repository

import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.LocalizacaoCineJoy
import com.example.cinejoy.data.model.Unidade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UnidadeRepository {
    fun unidadesPadrao(): List<Unidade> {
        val enderecoPadrao = "Shopping CineJoy Central"

        return LocalizacaoCineJoy.estados.flatMap { estado ->
            LocalizacaoCineJoy.cidadesDoEstado(estado.id).mapIndexed { index, cidade ->
                Unidade(
                    id = (estado.id * 10) + index + 1,
                    nome = enderecoPadrao,
                    cidade = cidade.nome,
                    estado = estado.nome,
                    siglaEstado = estado.sigla,
                    telefone = "",
                    endereco = enderecoPadrao,
                    idEstado = estado.id,
                    idCidade = cidade.id
                )
            }
        }
    }

    suspend fun listarUnidadesApi(): List<Unidade> = withContext(Dispatchers.IO) {
        runCatching {
            val resposta = CineJoyApi.getArray("/unidades")

            List(resposta.length()) { index ->
                val item = resposta.getJSONObject(index)

                Unidade(
                    id = item.optInt("idUnidade", index + 1),
                    nome = item.optString("nomeUni", item.optString("nome", "Shopping CineJoy Central")),
                    cidade = item.optString("nomeCidade", item.optString("cidade", "Campinas")),
                    estado = item.optString("nomeEstado", item.optString("estado", "São Paulo")),
                    siglaEstado = item.optString("siglaEstado", "SP"),
                    telefone = item.optString("telefoneUni", ""),
                    endereco = item.optString("endereco", "Shopping CineJoy Central")
                )
            }
        }.getOrElse {
            unidadesPadrao()
        }
    }
}