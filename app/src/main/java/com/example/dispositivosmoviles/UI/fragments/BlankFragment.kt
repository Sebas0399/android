package com.example.dispositivosmoviles.UI.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.Metodos

import com.example.dispositivosmoviles.UI.activities.DetailsPokeItem
import com.example.dispositivosmoviles.UI.activities.dataStore
import com.example.dispositivosmoviles.UI.adapters.PokemonAdapter
import com.example.dispositivosmoviles.data.user.UserDataStore
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.PokemonLogic.PokemonPetLogic
import com.example.dispositivosmoviles.logic.PokemonLogic.PokemonPetLogicDB
import com.example.dispositivosmoviles.logic.data.PokemonPet
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentBlankBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gmanager: GridLayoutManager

    private var pokemonPetItems: MutableList<PokemonPet> = mutableListOf()
    private lateinit var progressBar: ProgressBar

    private var rvAdapter: PokemonAdapter = PokemonAdapter { sendPokeItems(it) }
    private var offset = 0
    private val limit = 20
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBlankBinding.inflate(
            layoutInflater, container,
            false
        )
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        gmanager = GridLayoutManager(
            requireActivity(), 2
        )
        progressBar = binding.progressBar

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.Main) {
            getDataStore().collect(){
                user->
                Log.d("UCE",user.name)
                Log.d("UCE",user.email)
                Log.d("UCE",user.session)
            }
        }
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVDB()
            binding.rvSwipe.isRefreshing = false
        }
        // binding.listView.adapter = adapter
       /* if (pokemonPetItems.isEmpty()) {
            chargeDataRVAPI(limit, offset)

        }
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVAPI(limit, offset)
            binding.rvSwipe.isRefreshing = false
        }
        binding.rvMarvel.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount
                        Log.d("bur", v.toString())
                        Log.d("bur", p.toString())
                        Log.d("bur", t.toString())

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.IO)) {
                                val items = PokemonPetLogic().getAllPokemonPets(limit, offset)
                                /* val newItems = MarvelLogic().getAllCharacters(
                                     name="cap" ,
                                     5)*/
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(items)
                                    this@BlankFragment.offset += offset
                                    Log.d("offset", offset.toString())
                                }


                            }
                        }
                    }

                }

            })*/
       /* binding.txtfilter.addTextChangedListener { filteredText ->
            val newItems = pokemonPetItems.filter { items ->
                items.nombre.lowercase().contains(filteredText.toString().lowercase())

            }

            rvAdapter.replaceListAdapter(newItems)
        }*/
        chargeDataRVDB()


    }

    //se va a aenviar como parametro en el adaptador

    fun sendPokeItems(item: PokemonPet) {

        val i = Intent(requireActivity(), DetailsPokeItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }


    private fun chargeDataRV() {


        lifecycleScope.launch(Dispatchers.Main) {
            pokemonPetItems = withContext(Dispatchers.IO) {
                return@withContext (PokemonPetLogic().getAllPokemonPets(
                    20, 0


                ))
            } as MutableList<PokemonPet>

            rvAdapter.items = pokemonPetItems


            //JikanAnimeLogic().getAllAnimes()

            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/



            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }


        }
    }

    private fun chargeDataRVDB() {




            lifecycleScope.launch(Dispatchers.Main) {
                progressBar.visibility = View.VISIBLE

                pokemonPetItems = withContext(Dispatchers.IO) {

                    return@withContext PokemonPetLogicDB().getAllPokemonPets().toMutableList()
                }
                if(pokemonPetItems.isEmpty()){
                    Snackbar.make(binding.listView,"Favoritos vacio",Snackbar.LENGTH_SHORT).show()
                }
                rvAdapter.items = pokemonPetItems



                binding.rvMarvel.apply {
                    this.adapter = rvAdapter
                    //  this.layoutManager = lmanager
                    this.layoutManager = lmanager
                }
                progressBar.visibility = View.GONE


            }



    }

    private fun chargeDataRVAPI(limit: Int, offset: Int) {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {
                progressBar.visibility = View.VISIBLE

                pokemonPetItems = withContext(Dispatchers.IO) {

                    return@withContext (PokemonPetLogic().getAllPokemonPets(
                        limit,
                        offset
                    ) as MutableList<PokemonPet>)
                }
                rvAdapter.items = pokemonPetItems



                binding.rvMarvel.apply {
                    this.adapter = rvAdapter
                    //  this.layoutManager = lmanager
                    this.layoutManager = lmanager
                }
                progressBar.visibility = View.GONE


            }
        } else {
            Snackbar.make(
                binding.card, "No hay coneccion", Snackbar.LENGTH_LONG
            ).show()

        }

    }

    /*fun saveMarvelItem(item: MarvelHero):Boolean{
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                //Extenncion hacer
                DispositivosMoviles.getDbInstance().marvelDao().insertCharacter(item)
            }
        }
    }*/
    private fun getDataStore() =
        requireActivity().dataStore.data.map { prefs ->
            UserDataStore(
                name = prefs[stringPreferencesKey("usuario")].orEmpty(),
                email = prefs[stringPreferencesKey("email")].orEmpty(),
                session = prefs[stringPreferencesKey("session")].orEmpty()
            )
        }
}


