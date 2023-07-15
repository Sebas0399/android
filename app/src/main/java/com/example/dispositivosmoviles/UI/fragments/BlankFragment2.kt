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
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.UI.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.databinding.FragmentBlank2Binding
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogic
import com.example.dispositivosmoviles.logic.data.MarvelHero
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
    private var marvelCharacterItems: MutableList<MarvelHero> = mutableListOf<MarvelHero>()
    private lateinit var progressBar: ProgressBar
    private lateinit var rvAdapter: MarvelAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentBlank2Binding.inflate(layoutInflater,container,false)
        lmanager =LinearLayoutManager(
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
                        Log.d("bur",v.toString())
                        Log.d("bur",p.toString())
                        Log.d("bur",t.toString())   }
                }
            }
                )
        progressBar = binding.progressBar
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.txtBucar.addTextChangedListener{filteredText->

            Log.d("PROBANDO",filteredText.toString())
            if(filteredText.toString().isNotEmpty()){
                reset()
                chargeDataRV(filteredText.toString())

            }
            else{
                reset()
            }
//            val newItems= marvelCharacterItems.filter {
//                    items->
//                items.nombre.lowercase(). contains(filteredText.toString().lowercase())
//
//            }
//

        }
    }
    fun reset(){
        marvelCharacterItems=mutableListOf<MarvelHero>()
        rvAdapter.replaceListAdapter(marvelCharacterItems)
    }
    fun sendMarvelItems(item: MarvelHero) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }
    private fun chargeDataRV(nombre:String) {


        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelHeroLogic().getAllHero (nombre,5)


                )
            } as MutableList<MarvelHero>
            if(marvelCharacterItems.size==0){
                var f= Snackbar.make(binding.txtBucar, "No se encontro", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items =



                MarvelHeroLogic().getAllHero(nombre ,5)




            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE



        }
    }


}