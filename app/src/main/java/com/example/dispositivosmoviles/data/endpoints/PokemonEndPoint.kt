package com.example.dispositivosmoviles.data.endpoints

import com.example.dispositivosmoviles.data.entities.marvel.MarvelApiChars
import com.example.dispositivosmoviles.data.entities.pokemon.PokemonApiChars
import com.example.dispositivosmoviles.data.entities.pokemon.PokemonApiCharsAll
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonEndPoint {
    @GET("pokemon/")
    suspend fun getPokemon(
        @Query("name")name:String,
    ):Response<PokemonApiChars>
    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,

    ): Response<PokemonApiCharsAll>
}