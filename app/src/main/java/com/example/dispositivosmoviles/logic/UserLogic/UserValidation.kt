package com.example.dispositivosmoviles.logic.UserLogic

import com.example.dispositivosmoviles.data.user.UserLogin

class UserValidation {
    fun validateLogin(user:String,pass:String):Boolean{
        return(user== UserLogin().user&&pass== UserLogin().pass)
    }
}