package com.example.dispositivosmoviles.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels

import androidx.biometric.*
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.UI.viewModels.BiometricViewModel
import com.example.dispositivosmoviles.databinding.ActivityBiometricBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BiometricActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBiometricBinding
    private val biometricViewModel by viewModels<BiometricViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imagenFinger.setOnClickListener {
            autenticateBiometric()
        }
        biometricViewModel.isLoading.observe(this){
            isLoading->
            if(isLoading) {
                binding.lyMain.visibility = View.GONE
                binding.lyMainCopia.visibility=View.VISIBLE
            }
            else{
                binding.lyMain.visibility = View.VISIBLE
                binding.lyMainCopia.visibility=View.GONE
            }
        }
        lifecycleScope.launch {
            biometricViewModel.charginData()

        }
    }

    private fun autenticateBiometric() {
        if (checkBiometric()) {
            val executor = ContextCompat.getMainExecutor(this)
            val biometricPrompt = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion requerida")
                .setSubtitle("Ingrese su huella digital ")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                .setNegativeButtonText("Cancelar")
                .build()
            val biometricManager =
                BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
            biometricManager.authenticate(biometricPrompt)
        } else {
            Snackbar.make(
                this,
                binding.imagenFinger,
                "No existe los requsitos necesarios",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkBiometric(): Boolean {
        val biometricManager = BiometricManager.from(this)
        var returnValid:Boolean=false
        when (biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                returnValid= true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                returnValid= false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                returnValid= false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val intentPrompt = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                intentPrompt.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
                startActivity(intentPrompt)
                returnValid= false
            }
        }
        return returnValid
    }
}