package com.example.dispositivosmoviles.logic.PokemonLogic

import android.util.Log
import com.example.dispositivosmoviles.data.conections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.PokemonEndPoint
import com.example.dispositivosmoviles.data.entities.pokemon.Type
import com.example.dispositivosmoviles.data.entities.pokemon.database.PokemonPetDB
import com.example.dispositivosmoviles.data.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.logic.data.PokemonPet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonPetLogicDB {
    suspend fun getOnePokemon(name:String): PokemonPet {
        var m: PokemonPet = PokemonPet(0,"","","")
        var call= ApiConnection().getService(ApiConnection.TypeApi.Pokemon, PokemonEndPoint::class.java)
        if(call!=null){
            var response=call.getPokemon(name)

            if(response.isSuccessful){
                m= PokemonPet(response.body()!!.id,response.body()!!.name,getTipo(response.body()!!.types),response.body()!!.sprites.front_default)


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
    fun getTipo2(tipos:List<Type>):List<String>{
        var l= arrayListOf<String>()
        for(t in  tipos){
            l.add(t.type.name)
        }
        return l;
    }
    suspend fun getTipo(tipos:List<Type>):String{
        var l:String=""
        for(t in  tipos){
            l=(l.plus(" "+t.type.name))
        }

        return l;
    }
    suspend fun getAllPokemonPets():List<PokemonPet>{
        val itemList= arrayListOf<PokemonPet>()
        val aux=(DispositivosMoviles.getDbInstance().pokemonDao().getAllCharacters())
        aux.forEach{
            itemList.add(
                PokemonPet(
                    it.id,
                    it.nombre,
                    it.tipo,
                    it.foto
                )
            )
        }
        return itemList
    }
    suspend fun insertPokemonPetsDB(items:List<PokemonPet>){
        var itemsDB= arrayListOf<PokemonPetDB>()
        items.forEach{

            itemsDB.add(
                PokemonPetDB(
                it.id,
                it.nombre,
                it.tipos,
                it.foto
            )
            )
        }
        DispositivosMoviles.getDbInstance().pokemonDao().insertAllCharacter(itemsDB)
    }
    suspend fun getInitChars():MutableList<PokemonPet>{
        var items= mutableListOf<PokemonPet>()
        try {
            var items = PokemonPetLogicDB().getAllPokemonPets().toMutableList()

            if (items.isEmpty()) {
                items =
                    PokemonPetLogic().getAllPokemonPets(20, 0).toMutableList()
            }
            withContext(Dispatchers.IO) {
                PokemonPetLogicDB().insertPokemonPetsDB(items)
            }
            return items
        }
        catch (ex:Exception){
            throw RuntimeException(ex.message)
        }finally {
            return items
        }
    }
}
