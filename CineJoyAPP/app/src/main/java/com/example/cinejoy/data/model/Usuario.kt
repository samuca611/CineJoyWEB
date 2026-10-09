package com.example.cinejoy.data.model


data class Usuario(
    val cpf: String,
    val nome: String,
    val email: String,
    val dataNascimento: String,
    val telefone: String,
    val estado: Int,
    val cidade: Int,
    val userLogin: String,
    val senha: String = ""
)
