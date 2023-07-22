package com.example.dispositivosmoviles.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivosmoviles.logic.PokemonLogic.PokemonPetLogicDB
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.example.dispositivosmoviles.logic.data.PokemonPet
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsPokeItem : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMarvelItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val item=intent.getParcelableExtra<PokemonPet>("name")
        if(item!=null){
            binding.textoMarvel.text=item.nombre
            binding.comicMarvel.text=item.tipos
            Picasso.get().load(item.foto).into(binding.imagenMarvel)


        }
        binding.btnFav.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                if (item != null) {
                    withContext(Dispatchers.IO) {
                        PokemonPetLogicDB().insertPokemonPetDB(
                            PokemonPet(
                                item.id,
                                item.nombre,
                                item.tipos,
                                item.foto
                            )
                        )
                    }

                }
            }
        }
    }
}