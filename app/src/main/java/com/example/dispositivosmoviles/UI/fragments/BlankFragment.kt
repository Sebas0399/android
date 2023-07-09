package com.example.dispositivosmoviles.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.UI.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    private lateinit var gManager:GridLayoutManager
    private var marvelCharacterItems:MutableList<MarvelHero> = mutableListOf<MarvelHero>()
    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBlankBinding.inflate(layoutInflater,container,false)
        gManager=GridLayoutManager(requireActivity(),2)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
       /* val rvAdapter=MarvelAdapter(ListItems().returnMarvelChars())
        val rvMarvel=binding.rvMarvel
        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager=LinearLayoutManager(

            requireActivity(),LinearLayoutManager.VERTICAL,
            false

        )*/
        chargeDataRV()
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing=false
        }
        binding.txtfilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharacterItems.filter { items ->
                items.nombre.contains(filteredText.toString())

            }
            rvAdapter.replaceListAdapter(newItems)
        }
    }
    fun chargeDataRV(){
       lifecycleScope.launch(Dispatchers.IO){
           marvelCharacterItems = withContext(Dispatchers.IO) {


               return@withContext withContext(Dispatchers.IO) {
                   MarvelHeroLogic().getAllMarvelHeros(0, 99)
               }
           } as MutableList<MarvelHero>
            rvAdapter=MarvelAdapter(
                fnClick={sendMarvelItem(it)}
            )
           rvAdapter.items=marvelCharacterItems
               (binding.rvMarvel.apply {
                   this.adapter=rvAdapter
                   this.layoutManager=gManager
               })

           }
       }
    }
    fun sendMarvelItem(item: MarvelHero){

        val i= Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name",item)
        i.putExtra("comic",item)
        i.putExtra("imagen",item)
        startActivity(i)
    }

}