package com.example.dispositivosmoviles.data.entities.pokemon.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PokemonPetDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,val nombre:String,val tipo:String,val foto:String): Parcelable
