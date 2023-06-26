package com.example.dispositivosmoviles.logic

import com.example.dispositivosmoviles.logic.data.UserLogin

class UserValidation {
    fun validateLogin(user:String,pass:String):Boolean{
        return(user== UserLogin().user&&pass== UserLogin().pass)
    }
}