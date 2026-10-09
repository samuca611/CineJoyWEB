package com.example.cinejoy.data.repository

import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Sessao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object SessaoRepository {
    suspend fun listarSessoesPorFilme(idFilme: Int): List<Sessao> = withContext(Dispatchers.IO) {
        val resposta = CineJoyApi.getArray("/sessoes/filme/$idFilme")
        mapearSessoes(resposta, idFilme)
    }

    suspend fun listarSessoesPorFilmeEUnidade(idFilme: Int, idUnidade: Int): List<Sessao> = withContext(Dispatchers.IO) {
        val resposta = runCatching {
            CineJoyApi.getArray("/sessoes/filme/$idFilme/unidade/$idUnidade")
        }.getOrElse {
            CineJoyApi.getArray("/sessoes/filme/$idFilme")
        }

        mapearSessoes(resposta, idFilme)
            .filter { it.idUnidade == 0 || it.idUnidade == idUnidade }
    }

    suspend fun cadastrarSessao(
        idFilme: Int,
        idSala: Int,
        data: String,
        horario: String,
        idioma: String,
        formato: String
    ): Boolean = withContext(Dispatchers.IO) {
        CineJoyApi.post(
            "/sessoes",
            JSONObject()
                .put("idFilme", idFilme)
                .put("idSala", idSala)
                .put("dataHorario", "${normalizarData(data)}T$horario:00")
                .put("idioma", idioma)
                .put("formato", formato)
        )
    }

    private fun mapearSessoes(resposta: org.json.JSONArray, idFilme: Int): List<Sessao> {
        return List(resposta.length()) { index ->
            val item = resposta.getJSONObject(index)
            val dataHorario = item.optString("dataHorario")

            Sessao(
                id = item.optInt("idSessao", index + 1),
                idFilme = item.optInt("idFilme", idFilme),
                idSala = item.optInt("idSala", 1),
                horario = formatarHorario(dataHorario),
                sala = "Sala ${item.optString("numeroSala", item.optInt("idSala", 1).toString())}",
                data = formatarData(dataHorario),
                idioma = item.optString("idioma", "Dublado"),
                formato = item.optString("formato", "2D"),
                idUnidade = item.optInt("idUnidade", 0),
                unidade = item.optString("nomeUnidade", "")
            )
        }
    }

    private fun formatarData(dataHorario: String): String {
        val data = dataHorario.take(10)
        val partes = data.split("-")

        return if (partes.size == 3) "${partes[2]}/${partes[1]}" else data
    }

    private fun formatarHorario(dataHorario: String): String {
        return dataHorario.substringAfter("T", "")
            .take(5)
            .ifBlank { "--:--" }
    }

    private fun normalizarData(data: String): String {
        val partes = data.split("/", "-")

        return when {
            partes.size == 3 && data.contains("/") -> "${partes[2]}-${partes[1].padStart(2, '0')}-${partes[0].padStart(2, '0')}"
            partes.size == 3 -> data
            else -> data
        }
    }
}