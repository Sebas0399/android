package com.example.dispositivosmoviles.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}