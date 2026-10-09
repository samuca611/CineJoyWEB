package com.example.cinejoy.data.model

data class EstadoCineJoy(
    val id: Int,
    val nome: String,
    val sigla: String
) {
    val nomeCompleto: String
        get() = "$nome - $sigla"
}

data class CidadeCineJoy(
    val id: Int,
    val idEstado: Int,
    val nome: String
)

object LocalizacaoCineJoy {
    val estados = listOf(
        EstadoCineJoy(1, "São Paulo", "SP"),
        EstadoCineJoy(2, "Rio de Janeiro", "RJ"),
        EstadoCineJoy(3, "Minas Gerais", "MG"),
        EstadoCineJoy(4, "Paraná", "PR"),
        EstadoCineJoy(5, "Santa Catarina", "SC"),
        EstadoCineJoy(6, "Bahia", "BA"),
        EstadoCineJoy(7, "Pernambuco", "PE"),
        EstadoCineJoy(8, "Ceará", "CE"),
        EstadoCineJoy(9, "Goiás", "GO"),
        EstadoCineJoy(10, "Rio Grande do Sul", "RS")
    )

    val cidades = listOf(
        CidadeCineJoy(1, 1, "Campinas"),
        CidadeCineJoy(2, 1, "Santos"),

        CidadeCineJoy(3, 2, "Niterói"),
        CidadeCineJoy(4, 2, "Petrópolis"),

        CidadeCineJoy(5, 3, "Belo Horizonte"),
        CidadeCineJoy(6, 3, "Uberlândia"),

        CidadeCineJoy(7, 4, "Curitiba"),
        CidadeCineJoy(8, 4, "Londrina"),

        CidadeCineJoy(9, 5, "Florianópolis"),
        CidadeCineJoy(10, 5, "Joinville"),

        CidadeCineJoy(11, 6, "Salvador"),
        CidadeCineJoy(12, 6, "Feira de Santana"),

        CidadeCineJoy(13, 7, "Recife"),
        CidadeCineJoy(14, 7, "Olinda"),

        CidadeCineJoy(15, 8, "Fortaleza"),
        CidadeCineJoy(16, 8, "Juazeiro do Norte"),

        CidadeCineJoy(17, 9, "Goiânia"),
        CidadeCineJoy(18, 9, "Anápolis"),

        CidadeCineJoy(19, 10, "Porto Alegre"),
        CidadeCineJoy(20, 10, "Caxias do Sul")
    )

    fun cidadesDoEstado(idEstado: Int): List<CidadeCineJoy> {
        return cidades.filter { it.idEstado == idEstado }
    }

    fun estadoPorNomeCompleto(nomeCompleto: String): EstadoCineJoy? {
        return estados.find { it.nomeCompleto == nomeCompleto }
    }

    fun cidadePorNome(idEstado: Int, nomeCidade: String): CidadeCineJoy? {
        return cidades.find { it.idEstado == idEstado && it.nome == nomeCidade }
    }
}