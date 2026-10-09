package com.example.cinejoy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinejoy.data.model.Sessao
import com.example.cinejoy.data.repository.AlimentoRepository
import com.example.cinejoy.data.repository.AssentoRepository
import com.example.cinejoy.data.repository.AuthRepository
import com.example.cinejoy.data.repository.IngressoRepository
import com.example.cinejoy.data.repository.SessaoRepository
import com.example.cinejoy.data.repository.UnidadeRepository
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Comidas_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Conta_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Filmes_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Ingressos_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Menu_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Relatorio_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Unidades_screen
import com.example.cinejoy.ui.screens.ADMINISTRADOR_Usuarios_screen
import com.example.cinejoy.ui.screens.CadastroScreen
import com.example.cinejoy.ui.screens.LoginScreen
import com.example.cinejoy.ui.screens.UsuarioAlimentosScreen
import com.example.cinejoy.ui.screens.UsuarioAssentosScreen
import com.example.cinejoy.ui.screens.UsuarioComprovanteCompraScreen
import com.example.cinejoy.ui.screens.UsuarioContaScreen
import com.example.cinejoy.ui.screens.UsuarioFormaPagamentoScreen
import com.example.cinejoy.ui.screens.UsuarioMenuScreen
import com.example.cinejoy.ui.screens.UsuarioProgramacaoScreen
import com.example.cinejoy.ui.screens.UsuarioSessaoScreen
import com.example.cinejoy.ui.screens.UsuarioSinopseScreen
import com.example.cinejoy.ui.screens.UsuarioTopoActions
import com.example.cinejoy.ui.screens.UsuarioUnidadesScreen
import com.example.cinejoy.viewmodel.CompraViewModel
import com.example.cinejoy.viewmodel.FilmeViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val compraViewModel: CompraViewModel = viewModel()
    val filmeViewModel: FilmeViewModel = viewModel()

    var cadastroVeioDoPagamento by remember { mutableStateOf(false) }
    var alimentos by remember { mutableStateOf(AlimentoRepository.listarAlimentos()) }
    var assentos by remember { mutableStateOf(AssentoRepository.assentosPadrao()) }
    var unidades by remember { mutableStateOf(UnidadeRepository.unidadesPadrao()) }

    var mensagemLogin by remember { mutableStateOf("") }
    var mensagemCadastro by remember { mutableStateOf("") }
    var cadastrando by remember { mutableStateOf(false) }
    var mensagemAssentos by remember { mutableStateOf("") }
    var mensagemSessao by remember { mutableStateOf("") }
    var mensagemPagamento by remember { mutableStateOf("") }

    var modoVisitante by remember { mutableStateOf(false) }
    var sessoes by remember { mutableStateOf(emptyList<Sessao>()) }

    val scope = rememberCoroutineScope()

    fun voltarParaLogin() {
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.LOGIN) { inclusive = true }
        }
    }

    fun abrirMenuUsuario() {
        navController.navigate(Routes.USUARIO_MENU) {
            launchSingleTop = true
        }
    }

    fun abrirContaUsuario() {
        navController.navigate(Routes.USUARIO_CONTA) {
            launchSingleTop = true
        }
    }

    fun exigirAcessoAoApp(): Boolean {
        if (compraViewModel.usuarioLogado != null || modoVisitante) {
            return true
        }

        mensagemLogin = "Entre, cadastre-se ou use visitante para visualizar o app."
        voltarParaLogin()
        return false
    }

    SideEffect {
        UsuarioTopoActions.abrirConta = {
            abrirContaUsuario()
        }
    }

    LaunchedEffect(Unit) {
        runCatching { AlimentoRepository.listarAlimentosApi() }
            .onSuccess { alimentosApi ->
                if (alimentosApi.isNotEmpty()) {
                    alimentos = alimentosApi
                }
            }
    }

    LaunchedEffect(Unit) {
        runCatching { UnidadeRepository.listarUnidadesApi() }
            .onSuccess { unidadesApi ->
                if (unidadesApi.isNotEmpty()) {
                    unidades = unidadesApi
                }
            }
    }

    LaunchedEffect(compraViewModel.sessaoSelecionada?.idSala) {
        val idSala = compraViewModel.sessaoSelecionada?.idSala

        if (idSala != null) {
            assentos = runCatching { AssentoRepository.listarAssentosSala(idSala) }
                .getOrDefault(assentos)
        }
    }

    LaunchedEffect(compraViewModel.filmeSelecionado?.id, compraViewModel.unidadeSelecionada?.id) {
        val idFilme = compraViewModel.filmeSelecionado?.id
        val idUnidade = compraViewModel.unidadeSelecionada?.id

        sessoes = if (idFilme == null || idUnidade == null) {
            emptyList()
        } else {
            runCatching {
                SessaoRepository.listarSessoesPorFilmeEUnidade(idFilme, idUnidade)
            }.getOrDefault(emptyList())
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onCadastrarClick = {
                    mensagemCadastro = ""
                    cadastroVeioDoPagamento = false
                    navController.navigate(Routes.USUARIO_CADASTRO)
                },
                onEntrarClick = { login, senha ->
                    scope.launch {
                        val usuario = runCatching {
                            AuthRepository.loginUsuario(login, senha)
                        }.getOrNull()

                        if (usuario != null) {
                            compraViewModel.marcarUsuarioLogado(usuario)
                            modoVisitante = false
                            mensagemLogin = ""
                            navController.navigate(Routes.USUARIO_PROGRAMACAO)
                        } else {
                            mensagemLogin = "Login ou senha invalidos"
                        }
                    }
                },
                onVisitanteClick = {
                    modoVisitante = true
                    mensagemLogin = ""
                    navController.navigate(Routes.USUARIO_PROGRAMACAO)
                },
                onFuncionarioClick = { login, senha ->
                    scope.launch {
                        val entrou = if (login.isBlank() && senha.isBlank()) {
                            true
                        } else {
                            runCatching {
                                AuthRepository.loginFuncionario(login, senha)
                            }.getOrDefault(false)
                        }

                        if (entrou) {
                            mensagemLogin = ""
                            navController.navigate(Routes.ADMINISTRADOR_MENU)
                        } else {
                            mensagemLogin = "Login ou senha invalidos"
                        }
                    }
                },
                mensagem = mensagemLogin
            )
        }

        composable(Routes.USUARIO_MENU) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioMenuScreen(
                filmes = filmeViewModel.filmes,
                onProgramacaoClick = {
                    filmeViewModel.carregarFilmes()
                    navController.navigate(Routes.USUARIO_PROGRAMACAO)
                },
                onUnidadesClick = {
                    navController.navigate(Routes.USUARIO_UNIDADES)
                },
                onAlimentosClick = {
                    navController.navigate(Routes.USUARIO_ALIMENTOS)
                },
                onCadastroClick = {
                    abrirContaUsuario()
                },
                onFilmeClick = { filme ->
                    compraViewModel.selecionarFilme(filme)
                    navController.navigate(Routes.USUARIO_SINOPSE)
                },
                onSessaoFilmeClick = { filme ->
                    compraViewModel.selecionarFilme(filme)
                    navController.navigate(Routes.USUARIO_SESSAO)
                }
            )
        }

        composable(Routes.USUARIO_PROGRAMACAO) {
            if (!exigirAcessoAoApp()) return@composable

            LaunchedEffect(Unit) {
                filmeViewModel.carregarFilmes()
            }

            UsuarioProgramacaoScreen(
                filmes = filmeViewModel.filmes,
                onSinopseClick = { filme ->
                    compraViewModel.selecionarFilme(filme)
                    navController.navigate(Routes.USUARIO_SINOPSE)
                },
                onSessoesClick = { filme ->
                    compraViewModel.selecionarFilme(filme)
                    navController.navigate(Routes.USUARIO_SESSAO)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                },
                onUsuarioClick = {
                    abrirContaUsuario()
                }
            )
        }

        composable(Routes.USUARIO_CONTA) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioContaScreen(
                usuario = compraViewModel.usuarioLogado,
                visitante = modoVisitante,
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCadastrarClick = {
                    mensagemCadastro = ""
                    cadastroVeioDoPagamento = false
                    navController.navigate(Routes.USUARIO_CADASTRO)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_SINOPSE) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioSinopseScreen(
                filme = compraViewModel.filmeSelecionado,
                onProximoClick = {
                    navController.navigate(Routes.USUARIO_SESSAO)
                },
                onVoltarClick = {
                    navController.navigate(Routes.USUARIO_PROGRAMACAO) {
                        launchSingleTop = true
                    }
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_SESSAO) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioSessaoScreen(
                filme = compraViewModel.filmeSelecionado,
                unidadeSelecionada = compraViewModel.unidadeSelecionada,
                quantidadeIngressos = compraViewModel.quantidadeIngressos,
                sessoes = sessoes,
                sessaoSelecionada = compraViewModel.sessaoSelecionada,
                mensagem = mensagemSessao,
                onEscolherUnidadeClick = {
                    navController.navigate(Routes.USUARIO_UNIDADES)
                },
                onQuantidadeChange = { quantidade ->
                    compraViewModel.alterarQuantidadeIngressos(quantidade)
                    mensagemSessao = ""
                },
                onSessaoClick = { sessao ->
                    compraViewModel.selecionarSessao(sessao)
                    mensagemSessao = ""
                },
                onProximoClick = {
                    when {
                        compraViewModel.filmeSelecionado == null -> {
                            mensagemSessao = "Escolha um filme antes de continuar."
                        }

                        compraViewModel.unidadeSelecionada == null -> {
                            mensagemSessao = "Escolha estado, cidade e unidade CineJoy antes dos horários."
                        }

                        compraViewModel.sessaoSelecionada == null -> {
                            mensagemSessao = "Escolha horário e sala antes dos assentos."
                        }

                        compraViewModel.quantidadeIngressos <= 0 -> {
                            mensagemSessao = "Escolha a quantidade de ingressos."
                        }

                        else -> {
                            mensagemSessao = ""
                            navController.navigate(Routes.USUARIO_ASSENTOS)
                        }
                    }
                },
                onVoltarClick = {
                    navController.navigate(Routes.USUARIO_SINOPSE)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_UNIDADES) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioUnidadesScreen(
                unidades = unidades,
                unidadeSelecionada = compraViewModel.unidadeSelecionada,
                onUnidadeSelecionada = { unidade ->
                    compraViewModel.selecionarUnidade(unidade)
                    mensagemSessao = ""
                },
                onAplicarFiltroClick = {
                    navController.navigate(Routes.USUARIO_SESSAO)
                },
                onVoltarClick = {
                    navController.popBackStack()
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_ASSENTOS) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioAssentosScreen(
                assentos = assentos,
                selecionados = compraViewModel.assentosSelecionados,
                quantidadeIngressos = compraViewModel.quantidadeIngressos,
                unidade = compraViewModel.unidadeSelecionada,
                sessao = compraViewModel.sessaoSelecionada,
                mensagem = mensagemAssentos,
                onAssentoClick = { assento ->
                    mensagemAssentos = ""
                    compraViewModel.alternarAssento(assento)
                },
                onProximoClick = {
                    if (compraViewModel.assentosSelecionados.size < compraViewModel.quantidadeIngressos) {
                        mensagemAssentos = "Selecione ${compraViewModel.quantidadeIngressos} assento(s) antes de continuar."
                    } else {
                        mensagemAssentos = ""
                        navController.navigate(Routes.USUARIO_ALIMENTOS)
                    }
                },
                onVoltarClick = {
                    navController.navigate(Routes.USUARIO_SESSAO)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_ALIMENTOS) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioAlimentosScreen(
                alimentos = alimentos,
                onFinalizarClick = { alimentosEscolhidos ->
                    compraViewModel.selecionarAlimentos(alimentosEscolhidos)
                    navController.navigate(Routes.USUARIO_PAGAMENTO)
                },
                onVoltarClick = {
                    navController.popBackStack()
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_PAGAMENTO) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioFormaPagamentoScreen(
                usuarioCadastrado = compraViewModel.usuarioCadastrado,
                precoIngresso = compraViewModel.filmeSelecionado?.preco ?: "R$0.00",
                quantidadeIngressos = compraViewModel.quantidadeIngressos,
                totalCompra = compraViewModel.calcularTotal(),
                mensagem = mensagemPagamento,
                onCadastrarNecessario = {
                    cadastroVeioDoPagamento = true
                    navController.navigate(Routes.USUARIO_CADASTRO)
                },
                onFinalizarClick = {
                    val usuario = compraViewModel.usuarioLogado
                    val filme = compraViewModel.filmeSelecionado
                    val sessao = compraViewModel.sessaoSelecionada

                    if (usuario == null || filme == null || sessao == null || compraViewModel.assentosSelecionados.isEmpty()) {
                        mensagemPagamento = "Complete filme, sessao, cadeira e login antes de finalizar."
                    } else {
                        scope.launch {
                            mensagemPagamento = "Salvando compra no banco..."

                            val salvou = runCatching {
                                IngressoRepository.finalizarCompra(
                                    usuario = usuario,
                                    filme = filme,
                                    sessao = sessao,
                                    assentos = compraViewModel.assentosSelecionados,
                                    alimentos = compraViewModel.alimentosSelecionados,
                                    total = compraViewModel.calcularTotal()
                                )
                            }.getOrDefault(false)

                            if (salvou) {
                                mensagemPagamento = ""
                                assentos = runCatching {
                                    AssentoRepository.listarAssentosSala(sessao.idSala)
                                }.getOrDefault(assentos)

                                navController.navigate(Routes.USUARIO_COMPROVANTE)
                            } else {
                                mensagemPagamento = "Nao foi possivel salvar a compra. Verifique o banco/API."
                            }
                        }
                    }
                },
                onVoltarClick = {
                    navController.navigate(Routes.USUARIO_ALIMENTOS)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.USUARIO_CADASTRO) {
            CadastroScreen(
                mensagem = mensagemCadastro,
                carregando = cadastrando,
                onLogoClick = {
                    abrirMenuUsuario()
                },
                onValidarClick = { usuario ->
                    scope.launch {
                        cadastrando = true
                        mensagemCadastro = ""

                        val cadastrado = runCatching {
                            AuthRepository.cadastrarUsuario(usuario)
                        }.onFailure {
                            mensagemCadastro = it.message ?: "Erro ao cadastrar usuario."
                        }.getOrNull()

                        cadastrando = false

                        if (cadastrado != null) {
                            compraViewModel.marcarUsuarioLogado(cadastrado)
                            modoVisitante = false

                            if (cadastroVeioDoPagamento) {
                                cadastroVeioDoPagamento = false
                                navController.navigate(Routes.USUARIO_PAGAMENTO)
                            } else {
                                navController.navigate(Routes.USUARIO_CONTA)
                            }
                        }
                    }
                }
            )
        }

        composable(Routes.USUARIO_COMPROVANTE) {
            if (!exigirAcessoAoApp()) return@composable

            UsuarioComprovanteCompraScreen(
                compraViewModel = compraViewModel,
                onVoltarInicioClick = {
                    compraViewModel.limparCompra()
                    filmeViewModel.carregarFilmes()
                    navController.navigate(Routes.USUARIO_PROGRAMACAO)
                },
                onLogoClick = {
                    abrirMenuUsuario()
                }
            )
        }

        composable(Routes.ADMINISTRADOR_MENU) {
            ADMINISTRADOR_Menu_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_COMIDAS) {
            ADMINISTRADOR_Comidas_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_FILMES) {
            ADMINISTRADOR_Filmes_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_INGRESSOS) {
            ADMINISTRADOR_Ingressos_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_RELATORIO) {
            ADMINISTRADOR_Relatorio_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_UNIDADES) {
            ADMINISTRADOR_Unidades_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_USUARIOS) {
            ADMINISTRADOR_Usuarios_screen(navController)
        }

        composable(Routes.ADMINISTRADOR_CONTA) {
            ADMINISTRADOR_Conta_screen(navController)
        }
    }
}