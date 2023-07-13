package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelHeroDB
import com.example.dispositivosmoviles.logic.data.MarvelHero

@Dao
interface MarvelHeroDAO
{
    @Query("select * from MarvelHeroDB")
    fun getAllCharacters():List<MarvelHeroDB>
    @Query("select * from MarvelHeroDB where id=:id")
    fun getOneCharacter(id:Int):List<MarvelHeroDB>
    @Insert
    fun insertCharacter(ch:List<MarvelHeroDB>)
    @Query("select * from MarvelHeroDB where id=:id")
    fun deleteCharacter(id:Int):List<MarvelHeroDB>
    @Query("select * from MarvelHeroDB where id=:id")
    fun updateCharacter(id:Int):List<MarvelHeroDB>
}