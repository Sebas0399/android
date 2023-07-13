package com.example.dispositivosmoviles.data.entities.pokemon

data class PokemonApiCharsAll(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)