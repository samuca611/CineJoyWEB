import { useState } from "react";
import BotaoUsuario from "../../components/BotaoUsuario/BotaoUsuario.jsx";
import Cabecalho from "../../components/Cabecalho/Cabecalho.jsx";
import Rodape from "../../components/Rodape/Rodape.jsx";

// Componente principal responsável pela tela de cadastro de novos usuários
export default function TelaCadastro({
  abrirAlimentacao,
  abrirIngresso,
  abrirInicio,
  abrirLogin,
  abrirProgramacao,
  buscarFilme,
  cadastrarUsuario,
  cidades,
  estados,
  trocarUnidade,
  unidadeEscolhidaId,
  unidades,
  usuarioLogado,
  voltarInicio
}) {
  // armazena todos os valores digitados nos campos do formulário
  const [formulario, setFormulario] = useState({
    cpf: "",
    nome: "",
    email: "",
    dataNascimento: "",
    telefone: "",
    estado: 0,
    cidade: 0,
    estadoNome: "",
    estadoSigla: "",
    cidadeNome: "",
    userLogin: "",
    senha: ""
  });
  
  // armazena mensagens de erro ou de sucesso exibidas ao usuário
  const [mensagem, setMensagem] = useState("");

  // busca o objeto do estado correspondente com base no nome ou na sigla digitados
  const estadoEscolhido = estados.find((estado) => {
    const nomeIgual = estado.nome.toLowerCase() === formulario.estadoNome.toLowerCase();
    const siglaIgual = estado.sigla.toLowerCase() === formulario.estadoSigla.toLowerCase();

    return nomeIgual || siglaIgual;
  });

  // filtra as cidades pertencentes ao estado selecionado 
  const cidadesDoEstado = estadoEscolhido
    ? cidades.filter((cidade) => Number(cidade.estadoId) === Number(estadoEscolhido.id))
    : cidades;

  function mudarCampo(campo, valor) {
    setFormulario({ ...formulario, [campo]: valor });
  }

  function mudarEstado(valor) {
    const estado = estados.find((item) => {
      const mesmoNome = item.nome.toLowerCase() === valor.toLowerCase();
      const mesmaSigla = item.sigla.toLowerCase() === valor.toLowerCase();

      return mesmoNome || mesmaSigla;
    });

    setFormulario({
      ...formulario,
      estado: estado ? estado.id : 0,
      cidade: 0,
      estadoNome: estado ? estado.nome : valor,
      estadoSigla: estado ? estado.sigla : "",
      cidadeNome: ""
    });
  }

  // função acionada ao alterar a sigla do estado para atualizar os dados do formulário
  function mudarSigla(valor) {
    const sigla = valor.toUpperCase();
    const estado = estados.find((item) => item.sigla.toLowerCase() === sigla.toLowerCase());

    setFormulario({
      ...formulario,
      estado: estado ? estado.id : 0,
      cidade: 0,
      estadoNome: estado ? estado.nome : formulario.estadoNome,
      estadoSigla: sigla,
      cidadeNome: ""
    });
  }

  // função acionada ao alterar o campo de cidade para atualizar o ID e o nome correspondente 
  function mudarCidade(valor) {
    const cidade = cidadesDoEstado.find((item) => item.nome.toLowerCase() === valor.toLowerCase());

    setFormulario({
      ...formulario,
      cidade: cidade ? cidade.id : 0,
      cidadeNome: valor
    });
  }

  // função assíncrona que valida os dados preenchidos e envia a solicitação de cadastro para o sistema
  async function enviarCadastro(evento) {
    evento.preventDefault();
    setMensagem("");
    const cpf = formulario.cpf.replace(/\D/g, "");
    const uf = formulario.estadoSigla.trim();

    if (
      formulario.cpf === "" ||
      formulario.nome === "" ||
      formulario.email === "" ||
      formulario.dataNascimento === "" ||
      formulario.estadoNome === "" ||
      formulario.estadoSigla === "" ||
      formulario.cidadeNome === "" ||
      formulario.userLogin === "" ||
      formulario.senha === ""
    ) {
      setMensagem("Preencha todos os campos obrigatorios.");
      return;
    }

    if (cpf.length !== 11) {
      setMensagem("CPF precisa ter 11 numeros.");
      return;
    }

    if (uf.length !== 2) {
      setMensagem("UF precisa ter 2 letras.");
      return;
    }

    const resultado = await cadastrarUsuario(formulario);

    if (resultado.deuCerto) {
      setMensagem("Cadastro realizado com sucesso.");
    } else {
      setMensagem(resultado.mensagem);
    }
  }

  return (
    <>
      {/* componente de cabeçalho da página contendo a navegaçao principal */}
      <Cabecalho
        abrirAlimentacao={abrirAlimentacao}
        abrirIngresso={abrirIngresso}
        abrirInicio={abrirInicio}
        abrirLogin={abrirLogin}
        abrirProgramacao={abrirProgramacao}
        buscarFilme={buscarFilme}
        trocarUnidade={trocarUnidade}
        unidadeEscolhidaId={unidadeEscolhidaId}
        unidades={unidades}
        usuarioLogado={usuarioLogado}
      />

      <main className="cadastro-page">
        <section className="cadastro-banner"></section>
        <section className="cadastro-toolbar">
          {/* botão interativo que aciona a função de voltar para a página inicial */}
          <button type="button" onClick={voltarInicio}>
            Voltar para inicio
          </button>
          <div>
            <p>Cadastro CineJoy</p>
            <h1>Crie sua conta</h1>
          </div>
        </section>

        {/* formulário de cadastro do usuário */}
        <section className="cadastro-area">
          <form className="cadastro-form" onSubmit={enviarCadastro}>
            <label>
              CPF
              <input
                value={formulario.cpf}
                onChange={(evento) => mudarCampo("cpf", evento.target.value)}
                placeholder="11 numeros"
                maxLength="14"
              />
            </label>

            <label>
              Nome completo
              {/*entrada de dados */}
              <input
                value={formulario.nome}
                onChange={(evento) => mudarCampo("nome", evento.target.value)}
                maxLength="50"
              />
            </label>

            <label>
              Email
              {/*entrada de dados com tipo específico de email */}
              <input
                type="email"
                value={formulario.email}
                onChange={(evento) => mudarCampo("email", evento.target.value)}
                maxLength="60"
              />
            </label>

            <label>
              Data de nascimento
              {/* entrada de dados com tipo específico de data */}
              <input
                type="date"
                value={formulario.dataNascimento}
                onChange={(evento) => mudarCampo("dataNascimento", evento.target.value)}
              />
            </label>

            <label>
              Telefone
              {/*  entrada de dados controlada para o telefone */}
              <input
                value={formulario.telefone}
                onChange={(evento) => mudarCampo("telefone", evento.target.value)}
                placeholder="999999999"
                maxLength="11"
              />
            </label>

            <label>
              Estado
              <input
                list="lista-estados"
                value={formulario.estadoNome}
                onChange={(evento) => mudarEstado(evento.target.value)}
                maxLength="50"
              />
              {/*define as opções disponíveis para o campo de estado */}
              <datalist id="lista-estados">
                {estados.map((estado) => (
                  <option value={estado.nome} key={estado.id} />
                ))}
              </datalist>
            </label>
            
            <label>
              UF
              <input
                value={formulario.estadoSigla}
                onChange={(evento) => mudarSigla(evento.target.value)}
                maxLength="2"
                placeholder="SP"
              />
            </label>

            <label>
              Cidade
              <input
                list="lista-cidades"
                value={formulario.cidadeNome}
                onChange={(evento) => mudarCidade(evento.target.value)}
                maxLength="30"
              />
              <datalist id="lista-cidades">
                {cidadesDoEstado.map((cidade) => (
                  <option value={cidade.nome} key={cidade.id} />
                ))}
              </datalist>
            </label>

            <label>
              Login
              <input
                value={formulario.userLogin}
                onChange={(evento) => mudarCampo("userLogin", evento.target.value)}
                maxLength="25"
              />
            </label>

            <label>
              Senha
              <input
                type="password"
                value={formulario.senha}
                onChange={(evento) => mudarCampo("senha", evento.target.value)}
              />
            </label>

            {/* dispara o envio do formulário */}
            <button type="submit">Cadastrar</button>
            {mensagem !== "" && <p>{mensagem}</p>}
          </form>
        </section>
      </main>
      <Rodape />
      <BotaoUsuario />
    </>
  );
}