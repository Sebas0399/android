package com.example.dispositivosmoviles.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.ActivityNewBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.nextDown

class NewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    override fun onStart() {

        super.onStart()
        iniciar()
    }

    fun iniciar() {
        var name:String=""
        intent.extras.let {
           // name= it?.getString("var1")!!

        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when(item.itemId) {
                R.id.item_1 -> {
                    // Respond to navigation item 1 click
                    Snackbar.make(binding.textView, "UwU", Snackbar.LENGTH_LONG).show()
                    true
                }
                R.id.item_2 -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }
}