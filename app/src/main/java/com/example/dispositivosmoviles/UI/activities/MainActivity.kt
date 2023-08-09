package com.example.dispositivosmoviles.UI.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.utilities.MyLocationManager
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.UserLogic.UserValidation
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private val TAG = "UCE"
    private lateinit var auth: FirebaseAuth

    private var currentLocation: Location? = null

    @SuppressLint("MissingPermission")

    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->


            when (isGranted) {
                true -> {
                    client.checkLocationSettings(locationSettingsRequest).apply {
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener { location ->
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest, locationCallback, Looper.getMainLooper()
                                )
                            }
                        }
                        addOnFailureListener { ex ->
                            if (ex is ResolvableApiException) {
                                ex.startResolutionForResult(
                                    this@MainActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }

                        }


                    }

                }


                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    Snackbar.make(
                        binding.imageView,
                        "Ayude con el permiso porfa",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                false -> {
                    Snackbar.make(binding.imageView, "Permiso denegado", Snackbar.LENGTH_SHORT)
                        .show()

                }

                else -> {}
            }


        }
    private val speechToText =
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

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000
        ).build()
        auth = Firebase.auth
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d("UCE", "Ubicacion: ${location.latitude}, " + "${location.longitude}")
                    }
                }

            }
        }
        client = LocationServices.getSettingsClient(this)
        locationSettingsRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()

    }

    override fun onStart() {
        super.onStart()

        initClass()
        binding.btnIngresar.setOnClickListener {
            auth(binding.txtCorreo.text.toString(), binding.editTextTextPassword.text.toString())
        }

        binding.button2.setOnClickListener {
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

            /*  //val intent=Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com.ec"))
              //startActivity(intent)
              val intent = Intent(Intent.ACTION_WEB_SEARCH)
              intent.setClassName(
                  "com.google.android.googlequicksearchbox",
                  "com.google.android.googlequicksearchbox.SearchActivity"
              )
              intent.putExtra(SearchManager.QUERY, "UCE")
              startActivity(intent)*/

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
    private fun recovert(email: String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            task->
            if(task.isSuccessful){
                Toast.makeText(baseContext,"Correo enviado",Toast.LENGTH_SHORT)
            }
        }
    }

    private fun auth(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication successfull.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    private fun singIn(email: String,password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun initClass() {
        Log.d("UCE", "Entrando al onStart")
        /*  binding.button.setOnClickListener {
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

          }*/

    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivos@gmail.com"
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun test() {
        var location = MyLocationManager(this)
        location.getUserLocation()
    }
}