package com.example.dispositivosmoviles.logic.PokemonLogic

import android.util.Log
import com.example.dispositivosmoviles.data.conections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.example.dispositivosmoviles.data.endpoints.PokemonEndPoint
import com.example.dispositivosmoviles.data.entities.marvel.getMarvelHeros
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.example.dispositivosmoviles.logic.data.PokemonPet

class PokemonPetLogic {
    suspend fun getOnePokemon(name:String): PokemonPet {
        var m: PokemonPet =PokemonPet(0,"","","")
        var call=ApiConnection().getService(ApiConnection.TypeApi.Pokemon,PokemonEndPoint::class.java)
        if(call!=null){
            var response=call.getPokemon(name)
            if(response.isSuccessful){
                m=PokemonPet(response.body()!!.id,response.body()!!.name,response.body()!!.types.size.toString(),response.body()!!.sprites.other.dream_world.front_default)


            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        return m
    }

    suspend fun getAllPokemonPets(limit:Int,offset:Int):List<PokemonPet>{
        val itemList= arrayListOf<PokemonPet>()
        var call= ApiConnection().getService(ApiConnection.TypeApi.Pokemon, PokemonEndPoint::class.java)
        if(call!=null){
            var response=call.getAllPokemons(limit ,offset)
            if(response.isSuccessful){
                response.body()!!.results
                    .forEach{
                    val m=getOnePokemon(it.name)
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        return itemList
    }
}