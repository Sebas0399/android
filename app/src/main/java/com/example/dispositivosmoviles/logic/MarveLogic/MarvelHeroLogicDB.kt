package com.example.dispositivosmoviles.logic.MarveLogic

import com.example.dispositivosmoviles.data.conections.MarvelConnectionDB_Impl
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelHeroDB
import com.example.dispositivosmoviles.data.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.logic.data.MarvelHero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelHeroLogicDB {

    suspend fun getAllHero():List<MarvelHero>{
        val itemList= arrayListOf<MarvelHero>()
        val aux=(DispositivosMoviles.getDbInstance().marvelDao().getAllCharacters())
        aux.forEach{
            itemList.add(
                MarvelHero(
                    it.id,
                    it.nombre,
                    it.comic,
                    it.foto
                )
            )
        }
        return itemList
    }
    suspend fun insertMarvelCharsDB(items:List<MarvelHero>){
        var itemsDB= arrayListOf<MarvelHeroDB>()
        items.forEach{

            itemsDB.add(MarvelHeroDB(
                it.id,
                it.nombre,
                it.comic,
                it.foto
            ))
        }
        DispositivosMoviles.getDbInstance().marvelDao().insertCharacter(itemsDB)
    }
    suspend fun getInitChars():MutableList<MarvelHero>{
        var items= mutableListOf<MarvelHero>()
        try {
            var items = MarvelHeroLogicDB().getAllHero().toMutableList()

            if (items.isEmpty()) {
                items =
                    MarvelHeroLogic().getAllMarvelHeros(1, 99).toMutableList()
            }
            withContext(Dispatchers.IO) {
                MarvelHeroLogicDB().insertMarvelCharsDB(items)
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