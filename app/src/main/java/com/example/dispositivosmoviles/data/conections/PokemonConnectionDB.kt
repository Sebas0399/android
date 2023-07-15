package com.example.dispositivosmoviles.data.conections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.pokemon.PokemonPetDAO
import com.example.dispositivosmoviles.data.entities.pokemon.database.PokemonPetDB

@Database(entities = [PokemonPetDB::class],
    version = 1)
abstract class PokemonConnectionDB:RoomDatabase() {
    abstract fun pokemonDao(): PokemonPetDAO
}