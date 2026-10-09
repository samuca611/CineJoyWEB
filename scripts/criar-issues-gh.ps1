$repo = "samuca611/CineJoyWEB"

function CriarLabel($nome, $cor) {
    gh label create $nome --repo $repo --color $cor 2>$null
}

CriarLabel "responsavel: samuel" "f97316"
CriarLabel "responsavel: giovana" "ec4899"
CriarLabel "responsavel: henry" "3b82f6"
CriarLabel "responsavel: todos" "22c55e"
CriarLabel "documentation" "0ea5e9"
CriarLabel "sprint 1" "facc15"
CriarLabel "sprint 2" "fb923c"
CriarLabel "sprint 3" "a855f7"
CriarLabel "sprint 4" "14b8a6"
CriarLabel "frontend" "06b6d4"
CriarLabel "api" "8b5cf6"
CriarLabel "banco" "64748b"
CriarLabel "design" "f43f5e"
CriarLabel "testes" "84cc16"
CriarLabel "integracao" "0ea5e9"

$issues = @(
    @{
        titulo = "[Projeto] Backlog geral e divisao do CineJoy Web"
        labels = "documentation,responsavel: todos,sprint 1"
        corpo = @"
## Objetivo
Organizar o desenvolvimento do CineJoy Web em MVC, separando tarefas entre Samuel, Giovana e Henry.

## Integrantes

- Samuel: inicio, catalogo, programacao, compra de ingresso, filmes, sessoes e ingressos.
- Giovana: login, cadastro, alimentos, carrinho, pagamento, usuarios, produtos e pagamentos.
- Henry: painel funcionario, unidades, salas, funcionarios, conexao principal com SQL Server Regulus.
- Todos: revisao da conexao com o Regulus nas partes de cada integrante.

## Datas

- Sprint 1: 12/10/2026
- Sprint 2: 16/10/2026
- Sprint 3: 20/10/2026
- Sprint 4: 23/10/2026
"@
    },
    @{
        titulo = "[Samuel] Estruturar frontend MVC em Next.js"
        labels = "frontend,responsavel: samuel,sprint 1"
        corpo = @"
Responsavel: Samuel
Sprint: Sprint 1
Prazo: 12/10/2026
Status inicial: Concluido

## Checklist

- [x] Criar pasta frontend
- [x] Criar src/models
- [x] Criar src/views
- [x] Criar src/controllers
- [x] Testar build do frontend
"@
    },
    @{
        titulo = "[Samuel] Criar telas de inicio, catalogo e programacao"
        labels = "frontend,design,responsavel: samuel,sprint 2"
        corpo = @"
Responsavel: Samuel
Sprint: Sprint 2
Prazo: 16/10/2026
Status inicial: Em andamento

## Checklist

- [x] Criar tela inicial
- [x] Criar catalogo de filmes
- [x] Criar programacao
- [ ] Ajustar detalhes visuais finais
- [ ] Revisar responsividade
"@
    },
    @{
        titulo = "[Samuel] Criar tela de compra de ingresso e assentos"
        labels = "frontend,responsavel: samuel,sprint 2"
        corpo = @"
Responsavel: Samuel
Sprint: Sprint 2
Prazo: 16/10/2026
Status inicial: Em andamento

## Checklist

- [x] Criar tela de compra de ingresso
- [x] Criar selecao de assentos
- [ ] Enviar compra para API
- [ ] Validar assento escolhido
"@
    },
    @{
        titulo = "[Samuel] Criar API de filmes, sessoes e ingressos"
        labels = "api,responsavel: samuel,sprint 3"
        corpo = @"
Responsavel: Samuel
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: Em andamento

## Checklist

- [x] Criar Models
- [x] Criar Controllers
- [x] Criar Data fake inicial
- [ ] Ligar com SQL Server
- [ ] Testar endpoints
"@
    },
    @{
        titulo = "[Samuel] Criar SQL de filmes, sessoes e ingressos"
        labels = "banco,responsavel: samuel,sprint 3"
        corpo = @"
Responsavel: Samuel
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: Em andamento

## Checklist

- [x] Criar script inicial
- [ ] Testar script no SQL Server
- [ ] Ajustar relacionamentos
"@
    },
    @{
        titulo = "[Giovana] Criar telas de login e cadastro"
        labels = "frontend,responsavel: giovana,sprint 2"
        corpo = @"
Responsavel: Giovana
Sprint: Sprint 2
Prazo: 16/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar View de login
- [ ] Criar View de cadastro
- [ ] Criar Controller do usuario
- [ ] Validar campos obrigatorios
"@
    },
    @{
        titulo = "[Giovana] Criar telas de alimentos, carrinho e pagamento"
        labels = "frontend,design,responsavel: giovana,sprint 2"
        corpo = @"
Responsavel: Giovana
Sprint: Sprint 2
Prazo: 16/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar View de alimentos
- [ ] Criar carrinho
- [ ] Criar View de pagamento
- [ ] Calcular total
"@
    },
    @{
        titulo = "[Giovana] Criar API de usuarios, produtos e pagamentos"
        labels = "api,responsavel: giovana,sprint 3"
        corpo = @"
Responsavel: Giovana
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar Models
- [ ] Criar Controllers
- [ ] Criar Data inicial
- [ ] Testar endpoints
"@
    },
    @{
        titulo = "[Giovana] Criar SQL de usuarios, produtos e pagamentos"
        labels = "banco,responsavel: giovana,sprint 3"
        corpo = @"
Responsavel: Giovana
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar tabela usuario
- [ ] Criar tabela produto
- [ ] Criar tabela pagamento
- [ ] Testar no SQL Server
"@
    },
    @{
        titulo = "[Henry] Criar painel funcionario, unidades e salas"
        labels = "frontend,responsavel: henry,sprint 2"
        corpo = @"
Responsavel: Henry
Sprint: Sprint 2
Prazo: 16/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar View de funcionario
- [ ] Criar View de unidades
- [ ] Criar View de salas
- [ ] Organizar navegacao
"@
    },
    @{
        titulo = "[Henry] Criar API de unidades, salas e funcionarios"
        labels = "api,responsavel: henry,sprint 3"
        corpo = @"
Responsavel: Henry
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: A fazer

## Checklist

- [ ] Criar Models
- [ ] Criar Controllers
- [ ] Criar Data inicial
- [ ] Testar endpoints
"@
    },
    @{
        titulo = "[Henry] Configurar conexao principal com SQL Server Regulus"
        labels = "api,banco,integracao,responsavel: henry,sprint 3"
        corpo = @"
Responsavel: Henry
Apoio: Samuel e Giovana
Sprint: Sprint 3
Prazo: 20/10/2026
Status inicial: A fazer

## Checklist

- [ ] Configurar CONNECTION_STRING
- [ ] Configurar DbContext
- [ ] Testar conexao com SQL Server Regulus
- [ ] Deixar API rodando com dotnet run
- [ ] Documentar como rodar
"@
    },
    @{
        titulo = "[Todos] Definir contratos de integracao entre telas e API"
        labels = "integracao,responsavel: todos,sprint 1"
        corpo = @"
Responsavel: Todos
Sprint: Sprint 1
Prazo: 12/10/2026
Status inicial: Concluido

## Objetivo
Combinar os nomes dos endpoints, models e campos usados por cada parte antes de juntar os codigos.

## Checklist

- [x] Definir endpoints do Samuel
- [x] Definir endpoints da Giovana
- [x] Definir endpoints do Henry
- [x] Definir models principais
- [x] Separar pastas para evitar conflito
"@
    },
    @{
        titulo = "[Todos] Revisar conexoes entre as partes do grupo"
        labels = "integracao,testes,responsavel: todos,sprint 4"
        corpo = @"
Responsavel: Todos
Sprint: Sprint 4
Prazo: 23/10/2026
Status inicial: A fazer

## Objetivo
Conferir se as telas, controllers, models e scripts SQL estao conversando corretamente.

## Checklist

- [ ] Conferir chamadas do frontend para API
- [ ] Conferir nomes dos campos dos models
- [ ] Conferir scripts SQL
- [ ] Conferir se as partes nao alteraram arquivos umas das outras
- [ ] Testar fluxo com as partes integradas
"@
    },
    @{
        titulo = "[Todos] Validar Regulus nas partes de cada integrante"
        labels = "api,banco,integracao,testes,responsavel: todos,sprint 4"
        corpo = @"
Responsavel: Todos
Responsavel principal: Henry
Sprint: Sprint 4
Prazo: 23/10/2026
Status inicial: A fazer

## Objetivo
Testar a conexao com o Regulus em todas as partes do projeto.

## Checklist

- [ ] Samuel testar filmes, sessoes e ingressos no Regulus
- [ ] Giovana testar usuarios, produtos e pagamentos no Regulus
- [ ] Henry testar unidades, salas e funcionarios no Regulus
- [ ] Conferir se a API esta lendo CONNECTION_STRING
- [ ] Conferir se o frontend recebe dados reais da API
"@
    },
    @{
        titulo = "[Todos] Integrar partes do grupo no projeto principal"
        labels = "integracao,responsavel: todos,sprint 4"
        corpo = @"
Responsavel: Todos
Sprint: Sprint 4
Prazo: 23/10/2026
Status inicial: A fazer

## Checklist

- [ ] Integrar parte do Samuel
- [ ] Integrar parte da Giovana
- [ ] Integrar parte do Henry
- [ ] Resolver conflitos
"@
    },
    @{
        titulo = "[Todos] Revisar design e responsividade"
        labels = "design,responsavel: todos,sprint 4"
        corpo = @"
Responsavel: Todos
Sprint: Sprint 4
Prazo: 23/10/2026
Status inicial: A fazer

## Checklist

- [ ] Revisar cores
- [ ] Revisar fontes
- [ ] Testar em tela menor
- [ ] Corrigir desalinhamentos
"@
    },
    @{
        titulo = "[Todos] Testar fluxo completo do usuario"
        labels = "testes,responsavel: todos,sprint 4"
        corpo = @"
Responsavel: Todos
Sprint: Sprint 4
Prazo: 23/10/2026
Status inicial: A fazer

## Checklist

- [ ] Testar cadastro
- [ ] Testar login
- [ ] Testar escolha de filme
- [ ] Testar compra de ingresso
- [ ] Testar alimentos
- [ ] Testar pagamento
"@
    }
)

foreach ($issue in $issues) {
    $arquivo = New-TemporaryFile
    Set-Content -Path $arquivo -Value $issue.corpo -Encoding UTF8

    gh issue create `
        --repo $repo `
        --title $issue.titulo `
        --body-file $arquivo `
        --label $issue.labels

    Remove-Item $arquivo
}

Write-Output "Issues criadas. Agora abra o GitHub Project e adicione as issues ao quadro."

