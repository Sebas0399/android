package com.example.dispositivosmoviles.logic.MarveLogic

import android.util.Log
import com.example.dispositivosmoviles.data.conections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.example.dispositivosmoviles.data.entities.marvel.getMarvelHeros
import com.example.dispositivosmoviles.logic.data.MarvelHero

class MarvelHeroLogic {
    suspend fun getAllHero(name:String,limit:Int):List<MarvelHero>{
        val itemList= arrayListOf<MarvelHero>()
        var call=ApiConnection().getService(ApiConnection.TypeApi.Marvel,MarvelEndPoint::class.java)
        if(call!=null){
            var response=call.getCharactersStartsWith(name,limit)
            if(response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m=it.getMarvelHeros()
                    itemList.add(m)

                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        return itemList
    }
    suspend fun getAllMarvelHeros(offset:Int,limit:Int):List<MarvelHero>{
        val itemList= arrayListOf<MarvelHero>()
        var call=ApiConnection().getService(ApiConnection.TypeApi.Marvel,MarvelEndPoint::class.java)
        if(call!=null){
            var response=call.getAllMarvelChars(offset ,limit)
            if(response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m=it.getMarvelHeros()
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