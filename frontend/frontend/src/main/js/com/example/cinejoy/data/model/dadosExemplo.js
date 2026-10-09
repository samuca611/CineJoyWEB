export const filmesExemplo = [
  {
    id: 1,
    titulo: "Minions e Monstrons",
    genero: "Animacao",
    classificacao: "L",
    duracao: "90 min",
    descricao: "Aventura animada para toda a familia.",
    direcao: "",
    elenco: "",
    imagem: "/assets/poster-minions.png",
    sessoes: [
      { id: 1, data: "25/09", horario: "14:20", sala: "01", formato: "2D" },
      { id: 2, data: "25/09", horario: "17:40", sala: "01", formato: "2D" }
    ]
  },
  {
    id: 2,
    titulo: "Toy Story 5",
    genero: "Animacao",
    classificacao: "L",
    duracao: "90 min",
    descricao: "Novos desafios para os brinquedos mais queridos do cinema.",
    direcao: "",
    elenco: "",
    imagem: "/assets/poster-toy-story.png",
    sessoes: [
      { id: 3, data: "26/09", horario: "16:00", sala: "01", formato: "2D" }
    ]
  },
  {
    id: 3,
    titulo: "Homem Aranha: Um novo dia",
    genero: "Acao, Fantasia",
    classificacao: "12",
    duracao: "2hr 25min",
    descricao: "Direcao: Destin Daniel Cretton. Elenco: Tom Holland, Zendaya, Sadie Sink.",
    direcao: "Destin Daniel Cretton",
    elenco: "Tom Holland, Zendaya, Sadie Sink",
    imagem: "/assets/poster-homem-aranha-lista.png",
    posterCatalogo: "/assets/poster-homem-aranha.png",
    sessoes: [
      { id: 4, data: "27/09", horario: "19:30", sala: "01", formato: "3D" }
    ]
  },
  {
    id: 4,
    titulo: "A Odisseia",
    genero: "Acao, Fantasia",
    classificacao: "12",
    duracao: "2hr 53min",
    descricao: "Direcao: Christopher Nolan. Elenco: Matt Damon, Tom Holland, Anne Hathaway.",
    direcao: "Christopher Nolan",
    elenco: "Matt Damon, Tom Holland, Anne Hathaway",
    imagem: "/assets/poster-odisseia.png",
    sessoes: [
      { id: 5, data: "25/09", horario: "20:10", sala: "01", formato: "2D" }
    ]
  },
  {
    id: 5,
    titulo: "The worl's a little blurry",
    genero: "Documentario",
    classificacao: "12",
    duracao: "2hr 20min",
    descricao: "Direcao: R.J Cutler. Elenco: Billie Eilish, Finneas.",
    direcao: "R.J Cutler",
    elenco: "Billie Eilish, Finneas",
    imagem: "/assets/poster-billie.png",
    sessoes: [
      { id: 6, data: "25/09", horario: "21:00", sala: "01", formato: "2D" }
    ]
  }
];

export const produtosExemplo = [
  {
    id: 1,
    nome: "Combo CineJoy",
    preco: 30,
    descricao: "Pipoca grande + refrigerante",
    imagem: "/assets/pipoca.jpg"
  },
  {
    id: 2,
    nome: "Pipoca Grande",
    preco: 18,
    descricao: "Balde de pipoca tradicional",
    imagem: "/assets/pipoca.jpg"
  },
  {
    id: 3,
    nome: "Refrigerante",
    preco: 10,
    descricao: "Bebida gelada 500ml",
    imagem: "/assets/mario.jpg"
  },
  {
    id: 4,
    nome: "Combo Infantil",
    preco: 24,
    descricao: "Pipoca pequena + bebida",
    imagem: "/assets/ingresso.png"
  }
];

export function pegarDatas(filmes) {
  const datas = [];

  filmes.forEach((filme) => {
    filme.sessoes.forEach((sessao) => {
      if (!datas.includes(sessao.data)) {
        datas.push(sessao.data);
      }
    });
  });

  return datas.slice(0, 6);
}
