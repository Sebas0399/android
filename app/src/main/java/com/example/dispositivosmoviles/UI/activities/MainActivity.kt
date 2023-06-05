package com.example.dispositivosmoviles.UI.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("UCE","Entrando al onCreate")
        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClass()

    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {
        Log.d("UCE","Entrando al onStart")
        binding.button.setOnClickListener {
           /* if(UserValidator().validate(binding.txtUsuario.text.toString(),binding.txtPass.text.toString() )){
                var intent = Intent(this, NewActivity::class.java)
                intent.putExtra("var1",binding.txtUsuario.text.toString())
                startActivity(intent)
                }
                else{
                 var f= Snackbar.make(binding.btnLogIn, "matenme x2", Snackbar.LENGTH_LONG)

                f.show()
            }
*/

            //  binding.buscar.text="hola hundo"

            // boton1.text="Hola BB"
            // botonBuscar.text="Buscadito"
            // Toast.makeText(this, "Matenme", Toast.LENGTH_SHORT).show()

            // var f= Snackbar.make(binding.button, "matenme x2", Snackbar.LENGTH_LONG)

            //f.show()

        }

    }


}