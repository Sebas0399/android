package com.example.dispositivosmoviles.UI.activities

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.UserLogic.UserValidation
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()
        initClass()
        binding.button2.setOnClickListener {
            //val intent=Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com.ec"))
            //startActivity(intent)
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY, "UCE")
            startActivity(intent)

        }
        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->
                val sn = Snackbar.make(binding.imageView, "", Snackbar.LENGTH_SHORT)


                var msessage = when (resultActivity.resultCode) {
                    RESULT_OK -> {
                        sn.setBackgroundTint(resources.getColor(R.color.low_gray))
                        resultActivity.data?.getStringExtra("result").orEmpty()
                    }

                    RESULT_CANCELED -> {
                        sn.setBackgroundTint(resources.getColor(R.color.orange))

                        resultActivity.data?.getStringExtra("result").orEmpty()

                    }

                    else -> {
                        "Resultado dudoso "
                    }
                }
                sn.setText(msessage)
                sn.show()
            }


        val speechToText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                    activityResult ->
                val sn = Snackbar.make(binding.imageView, "", Snackbar.LENGTH_SHORT)
                var msessage = ""
                when (activityResult.resultCode) {
                    RESULT_OK -> {
                        val msg =
                            activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                                ?.get(0)
                                .toString()
                        val intent = Intent(Intent.ACTION_WEB_SEARCH)
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }

                    RESULT_CANCELED -> {
                        msessage = "Proceso cancelado"
                    }

                    else -> {
                        msessage = "Ocurrio un error"
                    }
                }
                sn.setText(msessage)
                sn.show()
            }
        binding.btnResult.setOnClickListener {
            //val resIntent = Intent(this, ResultActivity::class.java)
            //appResultLocal.launch(resIntent)
            val intentSpeach = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeach.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeach.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeach.putExtra(RecognizerIntent.EXTRA_PROMPT, "di algo...")
            speechToText.launch(intentSpeach)
        }
    }


    private fun initClass() {
        Log.d("UCE", "Entrando al onStart")
        binding.button.setOnClickListener {
            if (UserValidation().validateLogin(
                    binding.editTextTextEmailAddress.text.toString(),
                    binding.editTextTextPassword.text.toString()
                )
            ) {
                var intent = Intent(this, NewActivity::class.java)
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.d("UCE", "fallo")
                }
            } else {
                var f = Snackbar.make(
                    binding.button,
                    "Usuario y contraseña incorrectos",
                    Snackbar.LENGTH_LONG
                )

                f.show()
            }


            //  binding.buscar.text="hola hundo"

            // boton1.text="Hola BB"
            // botonBuscar.text="Buscadito"
            // Toast.makeText(this, "Matenme", Toast.LENGTH_SHORT).show()

            // var f= Snackbar.make(binding.button, "matenme x2", Snackbar.LENGTH_LONG)

            //f.show()

        }

    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivos@gmail.com"
        }
    }


}