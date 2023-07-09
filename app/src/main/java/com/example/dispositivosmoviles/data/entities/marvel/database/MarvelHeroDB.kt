package com.example.dispositivosmoviles.data.entities.marvel.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelHeroDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,val nombre:String,val comic:String,val foto:String): Parcelable