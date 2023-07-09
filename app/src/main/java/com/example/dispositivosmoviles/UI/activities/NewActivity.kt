package com.example.dispositivosmoviles.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.UI.fragments.BlankFragment
import com.example.dispositivosmoviles.UI.fragments.BlankFragment2
import com.example.dispositivosmoviles.UI.fragments.BlankFragment3
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
            val frag=BlankFragment()
            val frag2= BlankFragment2()
            val frag3= BlankFragment3()
            when(item.itemId) {
                R.id.item_1 -> {

                    // Respond to navigation item 1 click

                    val transaccion=supportFragmentManager.beginTransaction()
                    supportFragmentManager.popBackStack()
                    transaccion.add(binding.frmContainer.id,frag)
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                    true
                }
                R.id.item_2 -> {

                    // Respond to navigation item 1 click

                    val transaccion=supportFragmentManager.beginTransaction()
                    supportFragmentManager.popBackStack()
                    transaccion.add(binding.frmContainer.id,frag2)
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                    true
                }
                else -> false
            }
        }
    }
}