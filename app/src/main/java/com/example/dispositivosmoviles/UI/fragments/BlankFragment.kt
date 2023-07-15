package com.example.dispositivosmoviles.UI.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.Metodos

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.UI.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.data.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogic
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogicDB
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.Dispatchers
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

    private var marvelCharacterItems: MutableList<MarvelHero> = mutableListOf<MarvelHero>()

    private lateinit var rvAdapter: MarvelAdapter // MarvelAdapter { sendMarvelItems(it) }
    private var offset=0
    private val limit=99
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>("Carlos", "Juan", "Xavier", "Andres", "Pepe", "Antonio")


        // binding.listView.adapter = adapter
        chargeDataRVAPI(limit, offset)
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVAPI(limit,offset)
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
                        Log.d("bur",v.toString())
                        Log.d("bur",p.toString())
                        Log.d("bur",t.toString())

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.IO)) {
                                val items = MarvelHeroLogic().getAllMarvelHeros(offset,limit)
                                /* val newItems = MarvelLogic().getAllCharacters(
                                     name="cap" ,
                                     5)*/
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(items)
                                    this@BlankFragment.offset+=offset
                                    Log.d("offset",offset.toString())
                                }


                            }
                        }
                    }

                }

            })
        binding.txtfilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharacterItems.filter { items ->
                items.nombre.lowercase().contains(filteredText.toString().lowercase())

            }

            rvAdapter.replaceListAdapter(newItems)
        }

    }

    //se va a aenviar como parametro en el adaptador

    fun sendMarvelItems(item: MarvelHero) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }


    private fun chargeDataRV() {


        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelHeroLogic().getAllMarvelHeros(
                    0, 99


                ))
            } as MutableList<MarvelHero>

            rvAdapter.items = marvelCharacterItems


            //JikanAnimeLogic().getAllAnimes()

            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/



            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = gmanager
            }


        }
    }

    private fun chargeDataRVDB() {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {

                    return@withContext MarvelHeroLogicDB().getInitChars()
                }
                rvAdapter.items = marvelCharacterItems



                binding.rvMarvel.apply {
                    this.adapter = rvAdapter
                    //  this.layoutManager = lmanager
                    this.layoutManager = gmanager
                }


            }
        }
        else{
            Snackbar.make(
                binding.card,"No hay coneccion",Snackbar.LENGTH_LONG).show()

        }


    }
    private fun chargeDataRVAPI(limit:Int,offset:Int) {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {

                    return@withContext (MarvelHeroLogic().getAllMarvelHeros(limit,offset) as MutableList<MarvelHero>)
                }
                rvAdapter.items = marvelCharacterItems



                binding.rvMarvel.apply {
                    this.adapter = rvAdapter
                    //  this.layoutManager = lmanager
                    this.layoutManager = gmanager
                }


            }
        }
        else{
            Snackbar.make(
                binding.card,"No hay coneccion",Snackbar.LENGTH_LONG).show()

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
}


