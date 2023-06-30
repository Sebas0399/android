package com.example.dispositivosmoviles.data.conections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConnection {
    enum class TypeApi{
        Jikan,Marvel
    }
    private val API_MARVEL="https://gateway.marvel.com/v1/public/"
    fun getConnection(base:String): Retrofit{
        var retrofit=Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
    suspend fun <T,E:Enum<E>>getService(api:E,service:Class<T>):T{
        var BASE=""
        when(api.name){
            TypeApi.Marvel.name->{
                BASE=API_MARVEL
            }
        }
        return  getConnection(BASE).create(service)
    }
}