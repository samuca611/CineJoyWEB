package com.example.cinejoy.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinejoy.data.model.Filme
import com.example.cinejoy.data.repository.FilmeRepository
import kotlinx.coroutines.launch


class FilmeViewModel : ViewModel() {


    private var todosFilmes = FilmeRepository.listarFilmes()


    var filmes by mutableStateOf(todosFilmes)
        private set

    init {
        carregarFilmes()
    }


    fun buscarFilme(texto: String): List<Filme> {
        return if (texto.isBlank()) {
            todosFilmes
        } else {
            todosFilmes.filter {
                it.nome.contains(texto, ignoreCase = true)
            }
        }
    }


    fun buscarPorId(id: Int): Filme? {
        return todosFilmes.find { it.id == id }
    }

    fun carregarFilmes() {
        viewModelScope.launch {
            runCatching {
                FilmeRepository.listarFilmesApi()
            }.onSuccess { filmesApi ->
                if (filmesApi.isNotEmpty()) {
                    todosFilmes = filmesApi
                    filmes = filmesApi
                }
            }
        }
    }
}