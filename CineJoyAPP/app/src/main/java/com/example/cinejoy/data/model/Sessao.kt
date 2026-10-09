package com.example.cinejoy.data.model


data class Sessao(
    val id: Int,
    val idFilme: Int,
    val idSala: Int,
    val horario: String,
    val sala: String,
    val data: String,
    val idioma: String,
    val formato: String,
    val idUnidade: Int = 0,
    val unidade: String = ""
)