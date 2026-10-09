package com.example.cinejoy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinejoy.data.model.CidadeCineJoy
import com.example.cinejoy.data.model.EstadoCineJoy
import com.example.cinejoy.data.model.LocalizacaoCineJoy
import com.example.cinejoy.data.model.Usuario

@Composable
fun CadastroScreen(
    onValidarClick: (Usuario) -> Unit = {},
    mensagem: String = "",
    carregando: Boolean = false,
    onLogoClick: () -> Unit = {}
) {
    val scroll = rememberScrollState()

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var estadoSelecionado by remember { mutableStateOf<EstadoCineJoy?>(null) }
    var cidadeSelecionada by remember { mutableStateOf<CidadeCineJoy?>(null) }

    var erroLocal by remember { mutableStateOf("") }

    val cidadesDoEstado = estadoSelecionado?.let {
        LocalizacaoCineJoy.cidadesDoEstado(it.id)
    } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF8C8A3C), Color(0xFFEAEA6B))
                )
            )
    ) {
        TopoCineJoyUsuario(onLogoClick = onLogoClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp)
        ) {
            Text(
                text = "Cadastro CineJoy",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Titulo("Dados pessoais")

            Campo("Nome completo", valor = nome, aoMudar = { nome = it })

            Row {
                Campo("CPF", Modifier.weight(1f), cpf) {
                    cpf = it.filter(Char::isDigit).take(11)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Campo("Nascimento AAAA-MM-DD", Modifier.weight(1f), dataNascimento) {
                    dataNascimento = it.take(10)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Titulo("Contato")

            Campo("Email", valor = email, aoMudar = { email = it })

            Campo("Telefone com DDD", valor = telefone) {
                telefone = it.filter(Char::isDigit).take(11)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Titulo("Localização")

            CampoSelecaoCadastro(
                titulo = "Estado",
                valor = estadoSelecionado?.nomeCompleto.orEmpty(),
                placeholder = "Escolha o estado",
                opcoes = LocalizacaoCineJoy.estados.map { it.nomeCompleto },
                onSelecionar = { texto ->
                    estadoSelecionado = LocalizacaoCineJoy.estadoPorNomeCompleto(texto)
                    cidadeSelecionada = null
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            CampoSelecaoCadastro(
                titulo = "Cidade",
                valor = cidadeSelecionada?.nome.orEmpty(),
                placeholder = if (estadoSelecionado == null) "Escolha um estado primeiro" else "Escolha a cidade",
                opcoes = cidadesDoEstado.map { it.nome },
                habilitado = estadoSelecionado != null,
                onSelecionar = { nomeCidade ->
                    val estado = estadoSelecionado
                    if (estado != null) {
                        cidadeSelecionada = LocalizacaoCineJoy.cidadePorNome(estado.id, nomeCidade)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Titulo("Criação de login")

            Campo("Login de acesso", valor = login, aoMudar = { login = it })

            Row {
                Campo("Crie uma senha", Modifier.weight(1f), senha) {
                    senha = it
                }

                Spacer(modifier = Modifier.width(8.dp))

                Campo("Confirme sua senha", Modifier.weight(1f), confirmarSenha) {
                    confirmarSenha = it
                }
            }

            if (erroLocal.isNotBlank() || mensagem.isNotBlank()) {
                Text(
                    text = erroLocal.ifBlank { mensagem },
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Gray, RoundedCornerShape(12.dp))
                    .clickable {
                        erroLocal = validarCadastro(
                            nome = nome,
                            cpf = cpf,
                            dataNascimento = dataNascimento,
                            email = email,
                            telefone = telefone,
                            estadoSelecionado = estadoSelecionado,
                            cidadeSelecionada = cidadeSelecionada,
                            login = login,
                            senha = senha,
                            confirmarSenha = confirmarSenha
                        )

                        if (erroLocal.isBlank() && !carregando) {
                            onValidarClick(
                                Usuario(
                                    cpf = cpf,
                                    nome = nome,
                                    email = email,
                                    dataNascimento = dataNascimento,
                                    telefone = telefone,
                                    estado = estadoSelecionado?.id ?: 1,
                                    cidade = cidadeSelecionada?.id ?: 1,
                                    userLogin = login,
                                    senha = senha
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (carregando) "SALVANDO..." else "VALIDAR E CADASTRAR",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun Titulo(texto: String) {
    Text(
        text = texto,
        fontSize = 20.sp,
        color = Color(0xFF6E6E2E),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun Campo(
    texto: String,
    modifier: Modifier = Modifier,
    valor: String = "",
    aoMudar: (String) -> Unit = {}
) {
    TextField(
        value = valor,
        onValueChange = aoMudar,
        placeholder = { Text(texto) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun CampoSelecaoCadastro(
    titulo: String,
    valor: String,
    placeholder: String,
    opcoes: List<String>,
    habilitado: Boolean = true,
    onSelecionar: (String) -> Unit = {}
) {
    var aberto by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            color = Color(0xFF6E6E2E),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    if (habilitado) Color.White else Color.LightGray,
                    RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color(0xFF6E6E2E), RoundedCornerShape(20.dp))
                .clickable {
                    if (habilitado) {
                        aberto = !aberto
                    }
                }
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = valor.ifBlank { placeholder },
                color = if (valor.isBlank()) Color.Gray else Color.Black,
                fontSize = 15.sp
            )

            Text(
                text = "↓",
                color = Color.Gray,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        if (aberto && habilitado) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, Color.Gray)
            ) {
                if (opcoes.isEmpty()) {
                    OpcaoCadastro("Nenhuma opção encontrada") {
                        aberto = false
                    }
                } else {
                    opcoes.forEach { opcao ->
                        OpcaoCadastro(opcao) {
                            onSelecionar(opcao)
                            aberto = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OpcaoCadastro(
    texto: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .background(Color.White)
            .border(0.5.dp, Color.LightGray)
            .clickable { onClick() }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

private fun validarCadastro(
    nome: String,
    cpf: String,
    dataNascimento: String,
    email: String,
    telefone: String,
    estadoSelecionado: EstadoCineJoy?,
    cidadeSelecionada: CidadeCineJoy?,
    login: String,
    senha: String,
    confirmarSenha: String
): String {
    return when {
        nome.isBlank() -> "Informe o nome completo."
        cpf.length != 11 -> "Informe um CPF com 11 números."
        dataNascimento.length != 10 -> "Informe a data no formato AAAA-MM-DD."
        email.isBlank() || "@" !in email -> "Informe um e-mail válido."
        telefone.length != 11 -> "Informe o telefone com DDD. Exemplo: 19999999999."
        estadoSelecionado == null -> "Escolha um estado."
        cidadeSelecionada == null -> "Escolha uma cidade."
        login.isBlank() -> "Informe um login."
        senha.length < 6 -> "A senha precisa ter pelo menos 6 caracteres."
        senha != confirmarSenha -> "As senhas não conferem."
        else -> ""
    }
}