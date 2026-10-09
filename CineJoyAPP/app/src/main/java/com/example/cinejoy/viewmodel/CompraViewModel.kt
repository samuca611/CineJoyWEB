package com.example.cinejoy.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinejoy.data.model.Alimento
import com.example.cinejoy.data.model.Assento
import com.example.cinejoy.data.model.Filme
import com.example.cinejoy.data.model.Sessao
import com.example.cinejoy.data.model.Unidade
import com.example.cinejoy.data.model.Usuario


class CompraViewModel : ViewModel() {


    var filmeSelecionado by mutableStateOf<Filme?>(null)
        private set

    var unidadeSelecionada by mutableStateOf<Unidade?>(null)
        private set

    var quantidadeIngressos by mutableStateOf(1)
        private set

    var sessaoSelecionada by mutableStateOf<Sessao?>(null)
        private set


    var assentosSelecionados by mutableStateOf<List<Assento>>(emptyList())
        private set


    var alimentosSelecionados by mutableStateOf<List<Alimento>>(emptyList())
        private set

    var usuarioLogado by mutableStateOf<Usuario?>(null)
        private set

    val usuarioCadastrado: Boolean
        get() = usuarioLogado != null

    fun marcarUsuarioLogado(usuario: Usuario) {
        usuarioLogado = usuario
    }

    fun selecionarFilme(filme: Filme) {
        filmeSelecionado = filme
        unidadeSelecionada = null
        sessaoSelecionada = null
        assentosSelecionados = emptyList()
        alimentosSelecionados = emptyList()
        quantidadeIngressos = 1
    }

    fun selecionarUnidade(unidade: Unidade) {
        if (unidadeSelecionada?.id != unidade.id) {
            sessaoSelecionada = null
            assentosSelecionados = emptyList()
        }
        unidadeSelecionada = unidade
    }

    fun alterarQuantidadeIngressos(novaQuantidade: Int) {
        quantidadeIngressos = novaQuantidade.coerceIn(1, 10)
        if (assentosSelecionados.size > quantidadeIngressos) {
            assentosSelecionados = assentosSelecionados.take(quantidadeIngressos)
        }
    }

    fun selecionarSessao(sessao: Sessao) {
        if (sessaoSelecionada?.id != sessao.id) {
            assentosSelecionados = emptyList()
        }
        sessaoSelecionada = sessao
    }


    fun alternarAssento(assento: Assento) {
        assentosSelecionados = if (assentosSelecionados.any { it.id == assento.id }) {
            assentosSelecionados.filterNot { it.id == assento.id }
        } else {
            if (assentosSelecionados.size >= quantidadeIngressos) {
                assentosSelecionados
            } else {
                assentosSelecionados + assento
            }
        }
    }


    fun selecionarAlimentos(alimentos: List<Alimento>) {
        alimentosSelecionados = alimentos
    }


    fun calcularTotal(): Double {
        val valorIngresso = filmeSelecionado?.preco
            ?.replace("R$", "")
            ?.replace(",", ".")
            ?.trim()
            ?.toDoubleOrNull() ?: 0.0


        val totalAlimentos = alimentosSelecionados.sumOf { it.preco }

        return (valorIngresso * quantidadeIngressos) + totalAlimentos
    }


    fun limparCompra() {
        filmeSelecionado = null
        unidadeSelecionada = null
        sessaoSelecionada = null
        assentosSelecionados = emptyList()
        alimentosSelecionados = emptyList()
        quantidadeIngressos = 1
    }
}