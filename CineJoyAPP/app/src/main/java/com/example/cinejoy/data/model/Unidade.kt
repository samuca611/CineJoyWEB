package com.example.cinejoy.data.model


data class Unidade(
    val id: Int,
    val nome: String,
    val cidade: String,
    val estado: String,
    val siglaEstado: String,
    val telefone: String = "",
    val endereco: String = "",
    val idEstado: Int = 0,
    val idCidade: Int = 0
) {
    val enderecoResumido: String
        get() = listOf(nome, cidade, siglaEstado)
            .filter { it.isNotBlank() }
            .joinToString(" - ")
}