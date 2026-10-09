import { ChevronLeft, ChevronRight, MapPin } from "lucide-react";

function AbasDeData({ datas, dataEscolhida, trocarData }) {
  const dias = ["Sex.", "Sab.", "Dom.", "Seg.", "Ter.", "Qua."];

  return (
    <div className="date-tabs">
      <button type="button" aria-label="Data anterior">
        <ChevronLeft size={16} />
      </button>

      {datas.map((data, index) => (
        <button
          className={dataEscolhida === data ? "active" : ""}
          key={data}
          onClick={() => trocarData(data)}
          type="button"
        >
          <span>{dias[index] || "Dia"}</span>
          <strong>{data}</strong>
        </button>
      ))}

      <button type="button" aria-label="Proxima data">
        <ChevronRight size={16} />
      </button>
    </div>
  );
}

function FilmeDaLista({ filme, selecionarFilme }) {
  return (
    <article className="movie-item" onClick={() => selecionarFilme(filme)}>
      <img src={filme.imagem} alt={`Poster de ${filme.titulo}`} />

      <div className="movie-copy">
        <h3>{filme.titulo}</h3>
        <p>
          {filme.duracao || "2hr 25min"} {filme.genero}
        </p>
        <p>
          <strong>Direcao:</strong> {filme.direcao || "Nao informada"}
        </p>
        <p>
          <strong>Elenco:</strong> {filme.elenco || "Nao informado"}
        </p>
      </div>
    </article>
  );
}

function ordemDoFilme(filme) {
  const titulo = filme.titulo.toLowerCase();

  if (titulo.includes("aranha")) return 1;
  if (titulo.includes("odisseia")) return 2;
  if (titulo.includes("blurry")) return 3;
  return 4;
}

export default function Programacao({ filmes, produtos, textoBusca, unidadeEscolhida, unidades, datas, dataEscolhida, trocarData, selecionarFilme }) {
  const filmesDoDia = filmes
    .map((filme) => {
      return {
        ...filme,
        sessoes: (filme.sessoes || []).filter((sessao) => sessao.data === dataEscolhida)
      };
    })
    .filter((filme) => filme.sessoes.length > 0);

  const filmesParaMostrar = filmesDoDia.length > 0 ? filmesDoDia : filmes;
  const filmesOrdenados = [...filmesParaMostrar].sort((a, b) => {
    return ordemDoFilme(a) - ordemDoFilme(b);
  });
  const produto = produtos[0];
  const unidade = unidadeEscolhida || unidades[0];

  return (
    <section className="program-band" id="programacao">
      <div className="content-wrap program-grid">
        {produto && (
          <aside className="food-panel" id="alimentacao">
            <img src={produto.imagem || "/assets/pipoca.jpg"} alt={produto.nome} />
            <div>
              <p>Alimentacao</p>
              <h2>{produto.nome}</h2>
              <span>{produto.descricao}</span>
            </div>
          </aside>
        )}

        <div className="schedule-panel" id="ingresso">
          <div className="section-heading compact">
            <p>Em cartaz | Em breve</p>
            <span className="location">
              <MapPin size={15} />
              {unidade ? unidade.nome : "Nenhuma unidade cadastrada"}
            </span>
          </div>

          <AbasDeData
            datas={datas}
            dataEscolhida={dataEscolhida}
            trocarData={trocarData}
          />

          <div className="movie-list">
            {filmesOrdenados.length === 0 ? (
              <p className="empty-message">
                {textoBusca ? "Nenhum filme encontrado na busca." : "Nenhum filme cadastrado para a programacao."}
              </p>
            ) : filmesOrdenados.slice(0, 3).map((filme) => (
              <FilmeDaLista filme={filme} key={filme.id} selecionarFilme={selecionarFilme} />
            ))}
          </div>
        </div>
      </div>
    </section>
  );
}
