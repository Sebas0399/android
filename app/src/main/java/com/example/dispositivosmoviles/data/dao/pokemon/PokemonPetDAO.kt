package com.example.dispositivosmoviles.data.dao.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelHeroDB
import com.example.dispositivosmoviles.data.entities.pokemon.database.PokemonPetDB

@Dao
interface PokemonPetDAO {
    @Insert
    fun insertAllCharacter(ch:List<PokemonPetDB>)
    @Insert
    fun insertOneCharacter(ch:PokemonPetDB)
    @Query("select * from PokemonPetDB")
    fun getAllCharacters():List<PokemonPetDB>
}