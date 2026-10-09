package com.example.cinejoy.data.repository

import com.example.cinejoy.data.api.CineJoyApi
import com.example.cinejoy.data.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object AuthRepository {
    suspend fun loginUsuario(login: String, senha: String): Usuario? = withContext(Dispatchers.IO) {
        val resposta = CineJoyApi.postObject(
            "/usuarios/login",
            JSONObject()
                .put("userLogin", login)
                .put("senha", senha)
        )

        Usuario(
            cpf = resposta.optString("cpf"),
            nome = resposta.optString("nome"),
            email = resposta.optString("email"),
            dataNascimento = resposta.optString("dataNascimento", ""),
            telefone = resposta.optString("telefone"),
            estado = resposta.optInt("estado", 1),
            cidade = resposta.optInt("cidade", 1),
            userLogin = resposta.optString("userLogin")
        )
    }

    suspend fun loginFuncionario(login: String, senha: String): Boolean = withContext(Dispatchers.IO) {
        CineJoyApi.post(
            "/funcionarios/login",
            JSONObject()
                .put("userLoginFunc", login)
                .put("senhaFunc", senha)
        )
    }

    suspend fun cadastrarUsuario(usuario: Usuario): Usuario = withContext(Dispatchers.IO) {
        val dataApi = converterDataParaApi(usuario.dataNascimento)

        val resposta = CineJoyApi.postObject(
            "/usuarios",
            JSONObject()
                .put("cpf", usuario.cpf)
                .put("nome", usuario.nome)
                .put("email", usuario.email)
                .put("dataNascimento", "${dataApi}T00:00:00")
                .put("telefone", usuario.telefone)
                .put("estado", usuario.estado)
                .put("cidade", usuario.cidade)
                .put("userLogin", usuario.userLogin)
                .put("senha", usuario.senha)
        )

        usuario.copy(
            cpf = resposta.optString("cpf", usuario.cpf),
            dataNascimento = dataApi,
            senha = ""
        )
    }

    private fun converterDataParaApi(data: String): String {
        val valor = data.trim()

        return when {
            Regex("""\d{2}/\d{2}/\d{4}""").matches(valor) -> {
                val partes = valor.split("/")
                val dia = partes[0]
                val mes = partes[1]
                val ano = partes[2]

                "$ano-$mes-$dia"
            }

            Regex("""\d{4}-\d{2}-\d{2}""").matches(valor) -> {
                valor
            }

            else -> valor
        }
    }
}