package com.example.cinejoy.data.repository

import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Alimento
import com.example.cinejoy.data.model.Assento
import com.example.cinejoy.data.model.Filme
import com.example.cinejoy.data.model.Sessao
import com.example.cinejoy.data.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object IngressoRepository {
    suspend fun finalizarCompra(
        usuario: Usuario,
        filme: Filme,
        sessao: Sessao,
        assentos: List<Assento>,
        alimentos: List<Alimento>,
        total: Double
    ): Boolean = withContext(Dispatchers.IO) {
        val precoPorIngresso = if (assentos.isEmpty()) total else total / assentos.size
        val produtoConsumido = alimentos.firstOrNull()?.id ?: 1

        assentos.all { assento ->
            CineJoyApi.post(
                "/ingressos",
                JSONObject()
                    .put("precoIng", precoPorIngresso)
                    .put("idFilme", filme.id)
                    .put("usuario", usuario.cpf)
                    .put("sala", assento.idSala)
                    .put("sessao", "S${sessao.id.toString().padStart(2, '0')}")
                    .put("cadeira", assento.codigo)
                    .put("produtoConsum", produtoConsumido)
            )
        }
    }
}
