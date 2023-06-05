package com.example.dispositivosmoviles.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.ActivityNewBinding
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
            name= it?.getString("var1")!!

        }
        Log.d("UCE","Hola ${name}")
        binding.textView.text=name
        binding.button3.setOnClickListener {
            binding.textView.text = (Math.random() / Math.nextDown(1.0)).toString()
            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}