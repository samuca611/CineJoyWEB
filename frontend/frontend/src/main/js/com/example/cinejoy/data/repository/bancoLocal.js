const CHAVE_BANCO = "cinejoy-banco-local";

export const bancoVazio = {
  filmes: [],
  produtos: [],
  unidades: [],
  ingressos: [],
  logs: []
};

export function carregarBancoLocal() {
  const textoSalvo = localStorage.getItem(CHAVE_BANCO);

  if (!textoSalvo) {
    return bancoVazio;
  }

  try {
    const dados = JSON.parse(textoSalvo);
    return { ...bancoVazio, ...dados };
  } catch {
    return bancoVazio;
  }
}

export function salvarBancoLocal(dados) {
  localStorage.setItem(CHAVE_BANCO, JSON.stringify(dados));
}
