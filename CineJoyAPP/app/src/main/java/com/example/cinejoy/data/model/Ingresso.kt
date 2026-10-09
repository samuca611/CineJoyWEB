package com.example.cinejoy.data.model


data class Ingresso(
    val id: Int,
    val filme: Filme,
    val sessao: Sessao,
    val assentos: List<Assento>,
    val alimentos: List<Alimento>,
    val valorTotal: Double
)
