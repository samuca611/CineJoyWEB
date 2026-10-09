# Divisao do design aprovado

Este arquivo mostra como o design aprovado do CineJoy Web ficou dividido entre os integrantes.

## Samuel

Ficou com a parte principal do usuario:

- pagina inicial;
- cabecalho;
- banner de poster;
- catalogo;
- programacao;
- compra de ingresso;
- selecao de assentos;
- dados locais de filmes, sessoes e ingressos para teste das telas.

Arquivos principais:

- `frontend/src/app/page.jsx`
- `frontend/src/app/layout.jsx`
- `frontend/src/app/globals.css`
- `frontend/src/main/js/com/example/cinejoy/ui/screens/PaginaInicial/PaginaInicial.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/screens/TelaCompraIngresso/TelaCompraIngresso.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/components/Cabecalho/Cabecalho.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/components/Catalogo/Catalogo.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/components/Heroi/Heroi.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/components/Programacao/Programacao.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/components/Rodape/Rodape.jsx`
- `frontend/src/main/js/com/example/cinejoy/viewmodel/useCineJoyViewModel.js`
- `frontend/src/main/js/com/example/cinejoy/data/api/api.js`
- `frontend/src/main/js/com/example/cinejoy/data/model/dadosExemplo.js`

## Giovana

Ficou com a parte de conta, alimentos e pagamento:

- login;
- cadastro;
- alimentos;
- carrinho;
- pagamento;
- telas de usuarios, produtos e pagamentos.

Arquivos principais:

- `frontend/src/main/js/com/example/cinejoy/ui/components/ModalLogin/ModalLogin.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/screens/TelaCadastro/TelaCadastro.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/screens/TelaAlimentacao/TelaAlimentacao.jsx`
- `frontend/src/main/js/com/example/cinejoy/ui/screens/TelaPagamento/TelaPagamento.jsx`

## Henry

Ficou com a parte de funcionario, unidades e conexao:

- tela CineJoyfuls;
- area de funcionario;
- gerenciamento de filmes, alimentos, ingressos e unidades;
- login de funcionario;
- endpoints de unidades;
- tela visual para a area de administracao.

Arquivos principais:

- `frontend/src/main/js/com/example/cinejoy/ui/screens/TelaFuncionario/TelaFuncionario.jsx`

## Integracao

Alguns arquivos ligam as partes dos tres no frontend:

- `frontend/src/main/js/com/example/cinejoy/viewmodel/useCineJoyViewModel.js`
- `frontend/src/main/js/com/example/cinejoy/data/api/api.js`
- `frontend/src/main/js/com/example/cinejoy/data/model/dadosExemplo.js`
- `frontend/src/main/js/com/example/cinejoy/ui/styles.css`

Esses arquivos representam a revisao em grupo, porque conectam telas e dados locais de teste.
