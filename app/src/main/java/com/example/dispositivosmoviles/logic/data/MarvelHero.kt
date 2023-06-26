package com.example.dispositivosmoviles.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelHero(val id:Int,val nombre:String,val comic:String,val foto:String):Parcelable
