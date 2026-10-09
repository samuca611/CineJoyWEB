package com.example.cinejoy.data.model


data class Assento(
    val id: Int,
    val linha: String,
    val numero: Int,
    val ocupado: Boolean,
    val idSala: Int = 1
) {
    val codigo: String
        get() = "$linha${numero.toString().padStart(2, '0')}"
}
