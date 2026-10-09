import { filmesExemplo, produtosExemplo } from "../model/dadosExemplo.js";

let filmes = copiar(filmesExemplo);
let produtos = copiar(produtosExemplo);

let unidades = [
  {
    id: 1,
    nome: "CineJoy Centro",
    cep: "999999999",
    endereco: "Campinas",
    complemento: "SP",
    estadoId: 1,
    cidadeId: 1
  }
];

let ingressos = [
  {
    id: 1,
    usuario: "Cliente Teste",
    filme: "Homem Aranha: Um novo dia",
    assento: "A01",
    unidade: "CineJoy Centro - Sala 01 - Sessao 4",
    comida: "Combo CineJoy",
    status: "Realizado"
  }
];

const estados = [
  { id: 1, nome: "Sao Paulo", sigla: "SP" },
  { id: 2, nome: "Rio de Janeiro", sigla: "RJ" }
];

const cidades = [
  { id: 1, nome: "Campinas", estadoId: 1 },
  { id: 2, nome: "Mogi Mirim", estadoId: 1 },
  { id: 3, nome: "Sao Paulo", estadoId: 1 },
  { id: 4, nome: "Rio de Janeiro", estadoId: 2 }
];

function copiar(dados) {
  return JSON.parse(JSON.stringify(dados));
}

function esperar(dados) {
  return Promise.resolve(copiar(dados));
}

export function buscarDadosDaHome() {
  return esperar({
    filmes,
    produtos,
    unidades,
    ingressos,
    logs: []
  });
}

export function buscarLocalidades() {
  return esperar({ estados, cidades });
}

export function cadastrarUsuario(usuario) {
  return esperar({
    ...usuario,
    email: usuario.email || "cliente@cinejoy.com"
  });
}

export function loginFuncionario(email, senha) {
  if (email === "funcionario1@cinejoy.com" && senha === "cinejoy") {
    return esperar({
      idFunc: 1,
      nomeFunc: "Funcionario 1",
      emailFunc: email
    });
  }

  return Promise.reject(new Error("Funcionario nao encontrado."));
}

export function adicionarFilme(filme) {
  const novoFilme = { ...filme, id: Date.now() };
  filmes = [...filmes, novoFilme];
  return esperar(novoFilme);
}

export function editarFilme(filme) {
  filmes = filmes.map((item) => Number(item.id) === Number(filme.id) ? filme : item);
  return esperar(filme);
}

export function excluirFilme(id) {
  filmes = filmes.filter((filme) => Number(filme.id) !== Number(id));
  return Promise.resolve(null);
}

export function adicionarProduto(produto) {
  const novoProduto = { ...produto, id: Date.now() };
  produtos = [...produtos, novoProduto];
  return esperar(novoProduto);
}

export function editarProduto(produto) {
  produtos = produtos.map((item) => Number(item.id) === Number(produto.id) ? produto : item);
  return esperar(produto);
}

export function excluirProduto(id) {
  produtos = produtos.filter((produto) => Number(produto.id) !== Number(id));
  return Promise.resolve(null);
}

export function adicionarUnidade(unidade) {
  const novaUnidade = { ...unidade, id: Date.now() };
  unidades = [...unidades, novaUnidade];
  return esperar(novaUnidade);
}

export function editarUnidade(unidade) {
  unidades = unidades.map((item) => Number(item.id) === Number(unidade.id) ? unidade : item);
  return esperar(unidade);
}

export function excluirUnidade(id) {
  unidades = unidades.filter((unidade) => Number(unidade.id) !== Number(id));
  return Promise.resolve(null);
}

export function comprarIngresso(dados) {
  const ingresso = {
    id: Date.now(),
    usuario: dados.usuario || "Cliente",
    filme: dados.filme || "Filme",
    assento: dados.assentos?.join(", ") || "A01",
    unidade: "CineJoy Centro",
    comida: "Selecionada",
    status: "Realizado"
  };

  ingressos = [ingresso, ...ingressos];
  return esperar(ingresso);
}
