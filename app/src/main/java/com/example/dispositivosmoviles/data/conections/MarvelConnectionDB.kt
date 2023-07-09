package com.example.dispositivosmoviles.data.conections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.marvel.MarvelHeroDAO
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelHeroDB

@Database(entities = [MarvelHeroDB::class],
version = 1)
abstract class MarvelConnectionDB:RoomDatabase() {
    abstract fun marvelDao():MarvelHeroDAO
}