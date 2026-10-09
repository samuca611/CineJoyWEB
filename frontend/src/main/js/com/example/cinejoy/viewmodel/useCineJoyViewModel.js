import { useEffect, useState } from "react";
import {
  adicionarFilme,
  adicionarProduto,
  adicionarUnidade,
  buscarDadosDaHome,
  buscarLocalidades,
  cadastrarUsuario,
  comprarIngresso,
  editarFilme,
  editarProduto,
  editarUnidade,
  excluirFilme,
  excluirProduto,
  excluirUnidade,
  loginFuncionario
} from "../data/api/api.js";
import { pegarDatas } from "../data/model/dadosExemplo.js";

export function useCineJoyViewModel() {
  const [filmes, setFilmes] = useState([]);
  const [produtos, setProdutos] = useState([]);
  const [unidades, setUnidades] = useState([]);
  const [unidadeEscolhidaId, setUnidadeEscolhidaId] = useState("");
  const [estados, setEstados] = useState([]);
  const [cidades, setCidades] = useState([]);
  const [ingressos, setIngressos] = useState([]);
  const [logs, setLogs] = useState([]);
  const [erroApi, setErroApi] = useState("");
  const [dataEscolhida, setDataEscolhida] = useState("25/09");
  const [loginAberto, setLoginAberto] = useState(false);
  const [filmeCompra, setFilmeCompra] = useState(null);
  const [telaAlimentacao, setTelaAlimentacao] = useState(false);
  const [telaCadastro, setTelaCadastro] = useState(pegarHashAtual() === "#cadastro");
  const [usuarioLogado, setUsuarioLogado] = useState("");
  const [telaFuncionario, setTelaFuncionario] = useState(false);
  const datas = pegarDatas(filmes);
  const unidadeEscolhida = unidades.find((unidade) => String(unidade.id) === String(unidadeEscolhidaId)) || unidades[0] || null;

  useEffect(() => {
    carregarDados();
    carregarLocalidades();
  }, []);

  useEffect(() => {
    if (datas.length > 0 && !datas.includes(dataEscolhida)) {
      setDataEscolhida(datas[0]);
    }
  }, [datas, dataEscolhida]);

  useEffect(() => {
    const unidadeExiste = unidades.some((unidade) => String(unidade.id) === String(unidadeEscolhidaId));

    if (unidades.length > 0 && !unidadeExiste) {
      setUnidadeEscolhidaId(String(unidades[0].id));
    }
  }, [unidades, unidadeEscolhidaId]);

  async function carregarDados() {
    try {
      const dados = await buscarDadosDaHome();
      setFilmes(dados.filmes || []);
      setProdutos(dados.produtos || []);
      setUnidades(dados.unidades || []);
      setIngressos(dados.ingressos || []);
      setErroApi("");
    } catch {
      setErroApi("Nao foi possivel carregar os dados das telas.");
    }
  }

  async function carregarLocalidades() {
    try {
      const dados = await buscarLocalidades();
      setEstados(dados.estados || []);
      setCidades(dados.cidades || []);
    } catch {
      setEstados([]);
      setCidades([]);
    }
  }

  function registrarLog(tarefa) {
    const novoLog = {
      id: Date.now(),
      funcionario: usuarioLogado || "Funcionario 1",
      tarefa,
      status: "Concluido",
      data: new Date().toLocaleString("pt-BR")
    };

    setLogs((listaAtual) => [novoLog, ...listaAtual]);
  }

  async function entrar(email, senha) {
    if (email === "funcionario1@cinejoy.com") {
      try {
        const funcionario = await loginFuncionario(email, senha);
        setUsuarioLogado(funcionario.nomeFunc || "CineJoyfuls");
        setTelaFuncionario(true);
        setLoginAberto(false);
        setErroApi("");
      } catch {
        setErroApi("Login de funcionario nao autorizado pelo banco de dados.");
      }

      return;
    }

    setUsuarioLogado(email);
    setLoginAberto(false);
  }

  async function registrarIngresso(dadosDaCompra) {
    try {
      await comprarIngresso(dadosDaCompra);
      await carregarDados();
      registrarLog("Venda finalizada");
    } catch {
      setErroApi("Nao foi possivel salvar o ingresso no banco.");
    }
  }

  async function cadastrarNovoUsuario(dadosDoUsuario) {
    try {
      const usuario = await cadastrarUsuario(dadosDoUsuario);
      setUsuarioLogado(usuario.email || dadosDoUsuario.email);
      setTelaCadastro(false);
      setLoginAberto(false);
      window.location.hash = "#top";
      await carregarLocalidades();
      setErroApi("");
      return { deuCerto: true, mensagem: "" };
    } catch (erro) {
      const mensagem = erro.message || "Nao foi possivel cadastrar o usuario.";
      setErroApi(mensagem);
      return { deuCerto: false, mensagem };
    }
  }

  async function mudarFilmes(novaListaOuFuncao) {
    const novaLista = typeof novaListaOuFuncao === "function"
      ? novaListaOuFuncao(filmes)
      : novaListaOuFuncao;

    setFilmes(novaLista);

    try {
      await sincronizarLista(filmes, novaLista, adicionarFilme, editarFilme, excluirFilme);
      await carregarDados();
    } catch {
      setErroApi("Nao foi possivel salvar os filmes.");
      await carregarDados();
    }
  }

  async function mudarProdutos(novaListaOuFuncao) {
    const novaLista = typeof novaListaOuFuncao === "function"
      ? novaListaOuFuncao(produtos)
      : novaListaOuFuncao;

    setProdutos(novaLista);

    try {
      await sincronizarLista(produtos, novaLista, adicionarProduto, editarProduto, excluirProduto);
      await carregarDados();
    } catch {
      setErroApi("Nao foi possivel salvar os alimentos.");
      await carregarDados();
    }
  }

  async function mudarUnidades(novaListaOuFuncao) {
    const novaLista = typeof novaListaOuFuncao === "function"
      ? novaListaOuFuncao(unidades)
      : novaListaOuFuncao;

    setUnidades(novaLista);

    try {
      await sincronizarLista(unidades, novaLista, adicionarUnidade, editarUnidade, excluirUnidade);
      await carregarDados();
    } catch {
      setErroApi("Nao foi possivel salvar as unidades.");
      await carregarDados();
    }
  }

  async function mudarIngressos(novaListaOuFuncao) {
    const novaLista = typeof novaListaOuFuncao === "function"
      ? novaListaOuFuncao(ingressos)
      : novaListaOuFuncao;

    setIngressos(novaLista);
  }

  function irParaSecao(secao) {
    window.location.hash = secao;

    const elemento = document.querySelector(secao);
    if (elemento) {
      elemento.scrollIntoView();
    }
  }

  function voltarParaPrincipal(secao) {
    setTelaAlimentacao(false);
    setFilmeCompra(null);

    setTimeout(() => {
      irParaSecao(secao);
    }, 0);
  }

  return {
    dataEscolhida,
    datas,
    erroApi,
    estados,
    cidades,
    filmes,
    filmeCompra,
    ingressos,
    logs,
    loginAberto,
    produtos,
    telaAlimentacao,
    telaCadastro,
    telaFuncionario,
    unidadeEscolhida,
    unidadeEscolhidaId,
    unidades,
    usuarioLogado,
    cadastrarNovoUsuario,
    entrar,
    irParaSecao,
    mudarFilmes,
    mudarIngressos,
    mudarProdutos,
    mudarUnidades,
    registrarIngresso,
    registrarLog,
    setDataEscolhida,
    setFilmeCompra,
    setIngressos: mudarIngressos,
    setLoginAberto,
    setLogs,
    setProdutos: mudarProdutos,
    setTelaAlimentacao,
    setTelaCadastro,
    setTelaFuncionario,
    setUnidadeEscolhidaId,
    setUnidades: mudarUnidades,
    setFilmes: mudarFilmes,
    voltarParaPrincipal
  };
}

async function sincronizarLista(listaAntiga, listaNova, adicionar, editar, excluir) {
  const removidos = [];
  const adicionados = [];
  const editados = [];

  for (const antigo of listaAntiga) {
    const aindaExiste = listaNova.some((novo) => Number(novo.id) === Number(antigo.id));

    if (!aindaExiste) {
      removidos.push(antigo);
    }
  }

  for (const novo of listaNova) {
    const jaExistia = listaAntiga.some((antigo) => Number(antigo.id) === Number(novo.id));

    if (!jaExistia) {
      adicionados.push(novo);
    } else {
      editados.push(novo);
    }
  }

  for (const item of removidos) {
    await excluir(item.id);
  }

  for (const item of adicionados) {
    await adicionar(item);
  }

  for (const item of editados) {
    await editar(item);
  }
}

function pegarHashAtual() {
  if (typeof window === "undefined") {
    return "";
  }

  return window.location.hash;
}
