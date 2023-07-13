package com.example.dispositivosmoviles.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonPet(val id:Int,val nombre:String,val tipos:String,val foto:String): Parcelable