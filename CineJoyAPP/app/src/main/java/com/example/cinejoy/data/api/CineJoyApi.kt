package com.example.cinejoy.data.api

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object CineJoyApi {

    private const val HOST_API = "10.0.2.2"
    private const val PORTA_API = "5153"

    private const val BASE_URL = "http://$HOST_API:$PORTA_API"

    fun getArray(path: String): JSONArray {
        val (_, body) = request("GET", path, null)
        return JSONArray(body)
    }

    fun getObject(path: String): JSONObject {
        val (_, body) = request("GET", path, null)
        return JSONObject(body)
    }

    fun post(path: String, body: JSONObject): Boolean {
        val (status, _) = request("POST", path, body)
        return status in 200..299
    }

    fun postObject(path: String, body: JSONObject): JSONObject {
        val (_, resposta) = request("POST", path, body)
        return JSONObject(resposta)
    }

    fun put(path: String, body: JSONObject): Boolean {
        val (status, _) = request("PUT", path, body)
        return status in 200..299
    }

    fun putObject(path: String, body: JSONObject): JSONObject {
        val (_, resposta) = request("PUT", path, body)
        return if (resposta.isBlank()) JSONObject() else JSONObject(resposta)
    }

    fun delete(path: String): Boolean {
        val (status, _) = request("DELETE", path, null)
        return status in 200..299
    }

    private fun request(
        method: String,
        path: String,
        body: JSONObject?
    ): Pair<Int, String> {
        val url = URL(montarUrl(path))
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = method
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.setRequestProperty("Accept", "application/json")

            if (body != null) {
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")

                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(body.toString())
                }
            }

            val status = connection.responseCode

            val input = if (status in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val resposta = input?.let {
                BufferedReader(InputStreamReader(it)).use { reader ->
                    reader.readText()
                }
            }.orEmpty()

            if (status !in 200..299) {
                throw IllegalStateException(
                    resposta.ifBlank { "Erro HTTP $status na rota $path" }
                )
            }

            status to resposta
        } finally {
            connection.disconnect()
        }
    }

    private fun montarUrl(path: String): String {
        return if (path.startsWith("/")) {
            "$BASE_URL$path"
        } else {
            "$BASE_URL/$path"
        }
    }
}