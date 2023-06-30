package com.example.dispositivosmoviles.logic.MarveLogic

import android.util.Log
import com.example.dispositivosmoviles.data.conections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.example.dispositivosmoviles.data.marvel.MarvelHero

class MarvelHeroLogic {
    suspend fun getAllHero(name:String,limit:Int):List<MarvelHero>{
        val itemList= arrayListOf<MarvelHero>()
        var call=ApiConnection().getService(ApiConnection.TypeApi.Marvel,MarvelEndPoint::class.java)
        if(call!=null){
            var response=call.getCharactersStartsWith(name,limit)
            if(response.isSuccessful){
                response.body()!!.data.results.forEach{
                    var commic:String=""
                    if(it.comics.items.size>0){
                        commic=it.comics.items[0].name
                    }
                    val m=MarvelHero(
                        it.id,
                        it.name,
                        commic,
                        it.thumbnail.path+"."+it.thumbnail.extension
                    )
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