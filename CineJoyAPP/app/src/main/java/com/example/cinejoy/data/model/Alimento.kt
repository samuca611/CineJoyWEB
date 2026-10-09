package com.example.cinejoy.data.model

data class Alimento(
    val id: Int,
    val nome: String,
    val imagem: Int,
    val preco: Double,
    val descricao: String,
    val categoria: String = ""
)