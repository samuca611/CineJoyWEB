import { LogOut, Search, UserCircle } from "lucide-react";
import { useState } from "react";
import Rodape from "../../components/Rodape/Rodape.jsx";

const filmeVazio = {
  titulo: "",
  genero: "",
  classificacao: "L",
  duracao: "",
  direcao: "",
  elenco: "",
  imagem: "/assets/filme.jpg",
  posterCatalogo: "",
  dataSessao: "25/09",
  horarioSessao: "19:00",
  sala: "01",
  formato: "2D"
};

const alimentoVazio = {
  nome: "",
  descricao: "",
  preco: "",
  estoque: 0,
  imagem: "/assets/pipoca.jpg"
};

const unidadeVazia = {
  nome: "",
  cep: "",
  endereco: "",
  complemento: ""
};

function CabecalhoFuncionario({ pagina, trocarPagina, sair }) {
  return (
    <header className="func-header">
      <div className="func-strip">
        <button type="button" onClick={sair}>
          <LogOut size={18} />
          Sair
        </button>
        <UserCircle size={34} />
      </div>

      <div className="func-logo">
        <img src="/assets/logo-cinejoyfuls.png" alt="CineJoyfuls" />
      </div>

      <nav className="func-nav" aria-label="Navegacao funcionario">
        <button className={pagina === "cinema" ? "active" : ""} type="button" onClick={() => trocarPagina("cinema")}>
          Cinema
        </button>
        <button className={pagina === "alimentacao" ? "active" : ""} type="button" onClick={() => trocarPagina("alimentacao")}>
          Alimentacao
        </button>
        <button className={pagina === "ingressos" ? "active" : ""} type="button" onClick={() => trocarPagina("ingressos")}>
          Ingressos
        </button>
        <button className={pagina === "unidades" ? "active" : ""} type="button" onClick={() => trocarPagina("unidades")}>
          Unidades
        </button>
        <button className={pagina === "logs" ? "active" : ""} type="button" onClick={() => trocarPagina("logs")}>
          Logs
        </button>
        <label>
          <Search size={16} />
          <input placeholder="Buscar" />
        </label>
        <button className={pagina === "home" ? "active" : ""} type="button" onClick={() => trocarPagina("home")}>
          CineJoyfuls
        </button>
      </nav>
    </header>
  );
}

function LateralFuncionario({ titulo, imagem, children }) {
  return (
    <aside className="func-side">
      <h2>AREA DO FUNCIONARIO</h2>
      <label>
        Nome:
        <input value="Funcionario 1" readOnly />
      </label>
      <label>
        id:
        <input value="001" readOnly />
      </label>

      <div className="func-poster-card">
        <strong>{titulo}</strong>
        <img src={imagem} alt="" />
      </div>

      {children}
    </aside>
  );
}

function BotoesOperacao({ operacao, trocarOperacao }) {
  return (
    <div className="func-actions">
      <button className={operacao === "Editar" ? "active" : ""} type="button" onClick={() => trocarOperacao("Editar")}>
        Editar
      </button>
      <button className={operacao === "Incluir" ? "active" : ""} type="button" onClick={() => trocarOperacao("Incluir")}>
        Incluir
      </button>
      <button className={operacao === "Excluir" ? "active" : ""} type="button" onClick={() => trocarOperacao("Excluir")}>
        Excluir
      </button>
    </div>
  );
}

function textoVazio(tipo) {
  return (
    <div className="delete-box">
      <p>Nenhum {tipo} cadastrado. Use a opcao Incluir para adicionar.</p>
    </div>
  );
}

function formularioDoFilme(filme) {
  if (!filme) {
    return filmeVazio;
  }

  const sessao = filme.sessoes?.[0] || {};

  return {
    ...filme,
    dataSessao: sessao.data || "25/09",
    horarioSessao: sessao.horario || "19:00",
    sala: sessao.sala || "01",
    formato: sessao.formato || "2D"
  };
}

function dadosDoFilme(formulario, idAtual) {
  return {
    id: idAtual || Date.now(),
    titulo: formulario.titulo,
    genero: formulario.genero,
    classificacao: formulario.classificacao,
    duracao: formulario.duracao,
    direcao: formulario.direcao,
    elenco: formulario.elenco,
    imagem: formulario.imagem || "/assets/filme.jpg",
    posterCatalogo: formulario.posterCatalogo || formulario.imagem || "/assets/filme.jpg",
    sessoes: [
      {
        id: Date.now() + 1,
        data: formulario.dataSessao,
        horario: formulario.horarioSessao,
        sala: formulario.sala,
        formato: formulario.formato
      }
    ]
  };
}

function TelaCinema({ filmes, mudarFilmes, registrarLog }) {
  const [operacao, setOperacao] = useState("Incluir");
  const [filmeSelecionado, setFilmeSelecionado] = useState(filmes[0]?.id || "");
  const filmeAtual = filmes.find((filme) => filme.id === Number(filmeSelecionado));
  const [formulario, setFormulario] = useState(formularioDoFilme(filmeAtual));

  function escolherFilme(id) {
    const filme = filmes.find((item) => item.id === Number(id));
    setFilmeSelecionado(id);
    setFormulario(formularioDoFilme(filme));
  }

  function mudarCampo(campo, valor) {
    setFormulario({ ...formulario, [campo]: valor });
  }

  function trocarOperacao(novaOperacao) {
    setOperacao(novaOperacao);
    if (novaOperacao === "Incluir") {
      setFormulario(filmeVazio);
      setFilmeSelecionado("");
      return;
    }

    setFormulario(formularioDoFilme(filmeAtual || filmes[0]));
    setFilmeSelecionado(filmeAtual?.id || filmes[0]?.id || "");
  }

  function salvarFilme(evento) {
    evento.preventDefault();

    if (operacao === "Incluir") {
      const novoFilme = dadosDoFilme(formulario);
      mudarFilmes([...filmes, novoFilme]);
      registrarLog(`Adicionou filme: ${novoFilme.titulo}`);
      setFilmeSelecionado(novoFilme.id);
      setFormulario(formularioDoFilme(novoFilme));
      setOperacao("Editar");
      return;
    }

    const filmeEditado = dadosDoFilme(formulario, Number(filmeSelecionado));
    mudarFilmes(filmes.map((filme) => (filme.id === Number(filmeSelecionado) ? filmeEditado : filme)));
    registrarLog(`Editou filme: ${filmeEditado.titulo}`);
  }

  function excluirFilme() {
    const novaLista = filmes.filter((filme) => filme.id !== Number(filmeSelecionado));
    registrarLog(`Excluiu filme: ${filmeAtual?.titulo || filmeSelecionado}`);
    mudarFilmes(novaLista);
    setFilmeSelecionado(novaLista[0]?.id || "");
    setFormulario(formularioDoFilme(novaLista[0]));
    setOperacao(novaLista.length === 0 ? "Incluir" : "Editar");
  }

  const semCadastro = filmes.length === 0 && operacao !== "Incluir";

  return (
    <main className="func-main">
      <LateralFuncionario titulo={formulario.titulo || "Filme"} imagem={formulario.imagem || "/assets/filme.jpg"} />

      <section className="func-panel">
        <h1>Cadastro, edicao e exclusao de filmes</h1>
        <BotoesOperacao operacao={operacao} trocarOperacao={trocarOperacao} />

        {semCadastro ? textoVazio("filme") : (
          <>
            {operacao !== "Incluir" && (
              <label className="func-select">
                Filme
                <select value={filmeSelecionado} onChange={(evento) => escolherFilme(evento.target.value)}>
                  {filmes.map((filme) => (
                    <option key={filme.id} value={filme.id}>
                      {filme.titulo}
                    </option>
                  ))}
                </select>
              </label>
            )}

            {operacao === "Excluir" ? (
              <div className="delete-box">
                <p>Excluir o filme selecionado?</p>
                <button type="button" onClick={excluirFilme}>Excluir</button>
              </div>
            ) : (
              <form className="func-form" onSubmit={salvarFilme}>
                <label>Nome<input value={formulario.titulo} onChange={(evento) => mudarCampo("titulo", evento.target.value)} required /></label>
                <label>Genero<input value={formulario.genero} onChange={(evento) => mudarCampo("genero", evento.target.value)} /></label>
                <label>Duracao<input value={formulario.duracao} onChange={(evento) => mudarCampo("duracao", evento.target.value)} /></label>
                <label>Classificacao<input value={formulario.classificacao} onChange={(evento) => mudarCampo("classificacao", evento.target.value)} /></label>
                <label>Direcao<input value={formulario.direcao} onChange={(evento) => mudarCampo("direcao", evento.target.value)} /></label>
                <label>Elenco<input value={formulario.elenco} onChange={(evento) => mudarCampo("elenco", evento.target.value)} /></label>
                <label>Data da sessao<input value={formulario.dataSessao} onChange={(evento) => mudarCampo("dataSessao", evento.target.value)} /></label>
                <label>Horario<input value={formulario.horarioSessao} onChange={(evento) => mudarCampo("horarioSessao", evento.target.value)} /></label>
                <label>Sala<input value={formulario.sala} onChange={(evento) => mudarCampo("sala", evento.target.value)} /></label>
                <label>Formato<input value={formulario.formato} onChange={(evento) => mudarCampo("formato", evento.target.value)} /></label>
                <label className="wide">Imagem principal<input value={formulario.imagem} onChange={(evento) => mudarCampo("imagem", evento.target.value)} /></label>
                <label className="wide">Poster do catalogo<input value={formulario.posterCatalogo} onChange={(evento) => mudarCampo("posterCatalogo", evento.target.value)} /></label>
                <button type="submit">{operacao === "Incluir" ? "Adicionar" : "Salvar"}</button>
              </form>
            )}
          </>
        )}
      </section>
    </main>
  );
}

function TelaAlimentacaoFuncionario({ produtos, mudarProdutos, registrarLog }) {
  const [operacao, setOperacao] = useState("Incluir");
  const [idSelecionado, setIdSelecionado] = useState(produtos[0]?.id || "");
  const produtoAtual = produtos.find((produto) => produto.id === Number(idSelecionado));
  const [formulario, setFormulario] = useState(produtoAtual || alimentoVazio);

  function escolherProduto(id) {
    const produto = produtos.find((item) => item.id === Number(id));
    setIdSelecionado(id);
    setFormulario(produto || alimentoVazio);
  }

  function trocarOperacao(novaOperacao) {
    setOperacao(novaOperacao);
    if (novaOperacao === "Incluir") {
      setFormulario(alimentoVazio);
      setIdSelecionado("");
      return;
    }

    setFormulario(produtoAtual || produtos[0] || alimentoVazio);
    setIdSelecionado(produtoAtual?.id || produtos[0]?.id || "");
  }

  function salvarProduto(evento) {
    evento.preventDefault();
    const produtoSalvo = {
      ...formulario,
      id: operacao === "Incluir" ? Date.now() : Number(idSelecionado),
      preco: Number(String(formulario.preco).replace(",", ".")) || 0,
      estoque: Number(formulario.estoque) || 0,
      imagem: formulario.imagem || "/assets/pipoca.jpg"
    };

    if (operacao === "Incluir") {
      mudarProdutos([...produtos, produtoSalvo]);
      registrarLog(`Adicionou alimento: ${produtoSalvo.nome}`);
      setIdSelecionado(produtoSalvo.id);
      setOperacao("Editar");
    } else {
      mudarProdutos(produtos.map((produto) => (produto.id === Number(idSelecionado) ? produtoSalvo : produto)));
      registrarLog(`Editou alimento: ${produtoSalvo.nome}`);
    }

    setFormulario(produtoSalvo);
  }

  function excluirProduto() {
    const novaLista = produtos.filter((produto) => produto.id !== Number(idSelecionado));
    registrarLog(`Excluiu alimento: ${produtoAtual?.nome || idSelecionado}`);
    mudarProdutos(novaLista);
    setIdSelecionado(novaLista[0]?.id || "");
    setFormulario(novaLista[0] || alimentoVazio);
    setOperacao(novaLista.length === 0 ? "Incluir" : "Editar");
  }

  const semCadastro = produtos.length === 0 && operacao !== "Incluir";

  return (
    <main className="func-main">
      <LateralFuncionario titulo={formulario.nome || "Alimento"} imagem={formulario.imagem || "/assets/pipoca.jpg"} />
      <section className="func-panel">
        <h1>Cadastro, edicao e exclusao de alimentos</h1>
        <BotoesOperacao operacao={operacao} trocarOperacao={trocarOperacao} />

        {semCadastro ? textoVazio("alimento") : (
          <>
            {operacao !== "Incluir" && (
              <label className="func-select">
                Alimento
                <select value={idSelecionado} onChange={(evento) => escolherProduto(evento.target.value)}>
                  {produtos.map((produto) => <option key={produto.id} value={produto.id}>{produto.nome}</option>)}
                </select>
              </label>
            )}

            {operacao === "Excluir" ? (
              <div className="delete-box">
                <p>Excluir o alimento selecionado?</p>
                <button type="button" onClick={excluirProduto}>Excluir</button>
              </div>
            ) : (
              <form className="func-form" onSubmit={salvarProduto}>
                <label>Nome<input value={formulario.nome} onChange={(evento) => setFormulario({ ...formulario, nome: evento.target.value })} required /></label>
                <label>Preco R$<input value={formulario.preco} onChange={(evento) => setFormulario({ ...formulario, preco: evento.target.value })} /></label>
                <label>Estoque<input type="number" value={formulario.estoque} onChange={(evento) => setFormulario({ ...formulario, estoque: evento.target.value })} /></label>
                <label className="wide">Descricao<input value={formulario.descricao} onChange={(evento) => setFormulario({ ...formulario, descricao: evento.target.value })} /></label>
                <label className="wide">Imagem<input value={formulario.imagem} onChange={(evento) => setFormulario({ ...formulario, imagem: evento.target.value })} /></label>
                <button type="submit">{operacao === "Incluir" ? "Adicionar" : "Salvar"}</button>
              </form>
            )}
          </>
        )}
      </section>
    </main>
  );
}

function TelaIngressos({ ingressos, mudarIngressos, registrarLog }) {
  const [idSelecionado, setIdSelecionado] = useState(ingressos[0]?.id || "");
  const ingresso = ingressos.find((item) => item.id === Number(idSelecionado)) || ingressos[0];

  function trocarStatus(status) {
    mudarIngressos(ingressos.map((item) => (item.id === Number(idSelecionado) ? { ...item, status } : item)));
    registrarLog(`Atualizou ingresso #${idSelecionado} para ${status}`);
  }

  return (
    <main className="func-main">
      <LateralFuncionario titulo="Dados do ingresso" imagem="/assets/ingresso.png" />
      <section className="func-panel">
        <h1>Visualizacao do ingresso do usuario</h1>

        {ingressos.length === 0 ? textoVazio("ingresso") : (
          <>
            <label className="func-select">
              Ingresso
              <select value={idSelecionado} onChange={(evento) => setIdSelecionado(evento.target.value)}>
                {ingressos.map((item) => <option key={item.id} value={item.id}>#{item.id} - {item.usuario}</option>)}
              </select>
            </label>
            <div className="func-info-grid">
              <span>Nome do usuario<strong>{ingresso.usuario}</strong></span>
              <span>Id do ingresso<strong>{ingresso.id}</strong></span>
              <span>Comida<strong>{ingresso.comida}</strong></span>
              <span>Assento<strong>{ingresso.assento}</strong></span>
              <span>Unidade - Sala - Sessao<strong>{ingresso.unidade}</strong></span>
              <span>Filme<strong>{ingresso.filme}</strong></span>
            </div>
            <div className="payment-box">
              <p>Situacao do pagamento</p>
              <button type="button" onClick={() => trocarStatus("Realizado")}>Realizado</button>
              <button type="button" onClick={() => trocarStatus("Pendente")}>Pendente</button>
              <strong>{ingresso.status}</strong>
            </div>
          </>
        )}
      </section>
    </main>
  );
}

function TelaUnidades({ unidades, mudarUnidades, registrarLog }) {
  const [operacao, setOperacao] = useState("Incluir");
  const [idSelecionado, setIdSelecionado] = useState(unidades[0]?.id || "");
  const unidadeAtual = unidades.find((unidade) => unidade.id === Number(idSelecionado));
  const [formulario, setFormulario] = useState(unidadeAtual || unidadeVazia);

  function escolherUnidade(id) {
    const unidade = unidades.find((item) => item.id === Number(id));
    setIdSelecionado(id);
    setFormulario(unidade || unidadeVazia);
  }

  function trocarOperacao(novaOperacao) {
    setOperacao(novaOperacao);
    if (novaOperacao === "Incluir") {
      setFormulario(unidadeVazia);
      setIdSelecionado("");
      return;
    }

    setFormulario(unidadeAtual || unidades[0] || unidadeVazia);
    setIdSelecionado(unidadeAtual?.id || unidades[0]?.id || "");
  }

  function salvarUnidade(evento) {
    evento.preventDefault();
    const unidadeSalva = {
      ...formulario,
      id: operacao === "Incluir" ? Date.now() : Number(idSelecionado)
    };

    if (operacao === "Incluir") {
      mudarUnidades([...unidades, unidadeSalva]);
      registrarLog(`Adicionou unidade: ${unidadeSalva.nome}`);
      setIdSelecionado(unidadeSalva.id);
      setOperacao("Editar");
    } else {
      mudarUnidades(unidades.map((unidade) => (unidade.id === Number(idSelecionado) ? unidadeSalva : unidade)));
      registrarLog(`Editou unidade: ${unidadeSalva.nome}`);
    }

    setFormulario(unidadeSalva);
  }

  function excluirUnidade() {
    const novaLista = unidades.filter((unidade) => unidade.id !== Number(idSelecionado));
    registrarLog(`Excluiu unidade: ${unidadeAtual?.nome || idSelecionado}`);
    mudarUnidades(novaLista);
    setIdSelecionado(novaLista[0]?.id || "");
    setFormulario(novaLista[0] || unidadeVazia);
    setOperacao(novaLista.length === 0 ? "Incluir" : "Editar");
  }

  const semCadastro = unidades.length === 0 && operacao !== "Incluir";

  return (
    <main className="func-main">
      <LateralFuncionario titulo="Imagem da unidade" imagem="/assets/logo-cinejoy-footer.png" />
      <section className="func-panel">
        <h1>Unidades CineJoy</h1>
        <BotoesOperacao operacao={operacao} trocarOperacao={trocarOperacao} />

        {semCadastro ? textoVazio("unidade") : (
          <>
            {operacao !== "Incluir" && (
              <label className="func-select">
                Unidade
                <select value={idSelecionado} onChange={(evento) => escolherUnidade(evento.target.value)}>
                  {unidades.map((unidade) => <option key={unidade.id} value={unidade.id}>{unidade.nome}</option>)}
                </select>
              </label>
            )}

            {operacao === "Excluir" ? (
              <div className="delete-box">
                <p>Excluir a unidade selecionada?</p>
                <button type="button" onClick={excluirUnidade}>Excluir</button>
              </div>
            ) : (
              <form className="func-form" onSubmit={salvarUnidade}>
                <label>Nome da unidade<input value={formulario.nome} onChange={(evento) => setFormulario({ ...formulario, nome: evento.target.value })} required /></label>
                <label>CEP<input value={formulario.cep} onChange={(evento) => setFormulario({ ...formulario, cep: evento.target.value })} /></label>
                <label>Endereco<input value={formulario.endereco} onChange={(evento) => setFormulario({ ...formulario, endereco: evento.target.value })} /></label>
                <label>Complemento<input value={formulario.complemento} onChange={(evento) => setFormulario({ ...formulario, complemento: evento.target.value })} /></label>
                <button type="submit">{operacao === "Incluir" ? "Adicionar" : "Salvar"}</button>
              </form>
            )}
          </>
        )}
      </section>
    </main>
  );
}

function TelaLogs({ logs }) {
  return (
    <main className="func-main single">
      <section className="func-panel">
        <h1>Logs dos CineJoyfuls</h1>
        <div className="logs-table">
          <strong>Funcionario</strong>
          <strong>Tarefa</strong>
          <strong>Status</strong>
          <strong>Data/Hora</strong>
          {logs.length === 0 ? (
            <div className="logs-row">
              <span>Nenhum log registrado.</span>
              <span></span>
              <span></span>
              <span></span>
            </div>
          ) : logs.map((log) => (
            <div className="logs-row" key={log.id}>
              <span>{log.funcionario}</span>
              <span>{log.tarefa}</span>
              <span>{log.status}</span>
              <span>{log.data}</span>
            </div>
          ))}
        </div>
      </section>
    </main>
  );
}

function TelaHomeFuncionario({ trocarPagina }) {
  return (
    <main className="func-main single">
      <section className="func-panel home-panel">
        <img src="/assets/logo-cinejoyfuls.png" alt="CineJoyfuls" />
        <h1>Qual area deseja gerenciar?</h1>
        <div className="home-buttons">
          <button type="button" onClick={() => trocarPagina("cinema")}>Cinema</button>
          <button type="button" onClick={() => trocarPagina("alimentacao")}>Alimentacao</button>
          <button type="button" onClick={() => trocarPagina("ingressos")}>Ingressos</button>
          <button type="button" onClick={() => trocarPagina("unidades")}>Unidades</button>
          <button type="button" onClick={() => trocarPagina("logs")}>Logs</button>
        </div>
      </section>
    </main>
  );
}

export default function TelaFuncionario({
  filmes,
  ingressos,
  logs,
  mudarFilmes,
  mudarIngressos,
  mudarProdutos,
  mudarUnidades,
  produtos,
  registrarLog,
  sair,
  unidades
}) {
  const [pagina, setPagina] = useState("home");

  return (
    <>
      <CabecalhoFuncionario pagina={pagina} trocarPagina={setPagina} sair={sair} />
      {pagina === "home" && <TelaHomeFuncionario trocarPagina={setPagina} />}
      {pagina === "cinema" && <TelaCinema filmes={filmes} mudarFilmes={mudarFilmes} registrarLog={registrarLog} />}
      {pagina === "alimentacao" && <TelaAlimentacaoFuncionario produtos={produtos} mudarProdutos={mudarProdutos} registrarLog={registrarLog} />}
      {pagina === "ingressos" && <TelaIngressos ingressos={ingressos} mudarIngressos={mudarIngressos} registrarLog={registrarLog} />}
      {pagina === "unidades" && <TelaUnidades unidades={unidades} mudarUnidades={mudarUnidades} registrarLog={registrarLog} />}
      {pagina === "logs" && <TelaLogs logs={logs} />}
      <Rodape />
    </>
  );
}
