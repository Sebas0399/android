package com.example.dispositivosmoviles.data.utilities

import android.app.Application
import androidx.room.Room
import com.example.dispositivosmoviles.data.conections.MarvelConnectionDB
import com.example.dispositivosmoviles.data.conections.PokemonConnectionDB
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelHeroDB

class DispositivosMoviles:Application() {
    val name_class:String="Admin"
    override fun onCreate() {
        super.onCreate()
        db=Room.databaseBuilder(applicationContext,PokemonConnectionDB::class.java,"pokemonDB").build()

    }

    companion object{
        val name_companion:String="Admin"
        private var db:PokemonConnectionDB ?= null
        fun getDbInstance():PokemonConnectionDB{
            return db!!
        }

    }
}