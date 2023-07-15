package com.example.dispositivosmoviles.data.conections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConnection {
    enum class TypeApi{
        Pokemon,Marvel
    }
    private val API_MARVEL="https://gateway.marvel.com/v1/public/"
    private val API_POKEMON="https://pokeapi.co/api/v2/"

    fun getConnection(base: String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    suspend fun <T,E:Enum<E>>getService(api:E,service:Class<T>):T{
        var BASE=""
        when(api.name){
            TypeApi.Marvel.name->{
                BASE=API_MARVEL
            }
            TypeApi.Pokemon.name->{
                BASE=API_POKEMON
            }
        }
        return  getConnection(BASE).create(service)
    }
}