package com.example.dispositivosmoviles.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.UI.activities.DetailsPokeItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.UI.adapters.PokemonAdapter
import com.example.dispositivosmoviles.databinding.FragmentBlank2Binding
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogic
import com.example.dispositivosmoviles.logic.PokemonLogic.PokemonPetLogic
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.example.dispositivosmoviles.logic.data.PokemonPet
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {
    private lateinit var binding: FragmentBlank2Binding
    private lateinit var lmanager: LinearLayoutManager
    private var pokemonPetItems: MutableList<PokemonPet> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private var rvAdapter: PokemonAdapter = PokemonAdapter { sendPokeItems(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlank2Binding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvMarvel.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount
                    }
                }
            }
        )
        progressBar = binding.progressBar
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnBuscar.setOnClickListener{
            chargeDataRV(binding.txtBucar.text.toString())
        }
    }
    fun sendPokeItems(item: PokemonPet) {

        val i = Intent(requireActivity(), DetailsPokeItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }
    fun reset() {
        pokemonPetItems = mutableListOf<PokemonPet>()
        rvAdapter.replaceListAdapter(pokemonPetItems)
    }

    fun sendMarvelItems(item: MarvelHero) {

        val i = Intent(requireActivity(), DetailsPokeItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun chargeDataRV(nombre: String) {


        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            pokemonPetItems = withContext(Dispatchers.IO) {
                return@withContext listOf(PokemonPetLogic().getOnePokemon(nombre)


                        )
            } as MutableList<PokemonPet>
            if (pokemonPetItems.size == 0) {
                var f = Snackbar.make(binding.txtBucar, "No se encontro", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items = listOf(PokemonPetLogic().getOnePokemon(nombre))







            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE


        }
    }


}