package com.example.dispositivosmoviles.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMarvelItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val item=intent.getParcelableExtra<MarvelHero>("name")
        if(item!=null){
            binding.textoMarvel.text=item.nombre
            binding.comicMarvel.text=item.comic
            Picasso.get().load(item.foto).into(binding.imagenMarvel)


        }
    }
}