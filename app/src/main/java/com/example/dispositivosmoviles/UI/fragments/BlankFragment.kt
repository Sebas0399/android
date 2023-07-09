package com.example.dispositivosmoviles.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.UI.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.ListItems
import com.example.dispositivosmoviles.data.marvel.MarvelHero
import com.example.dispositivosmoviles.logic.MarveLogic.MarvelHeroLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBlankBinding.inflate(layoutInflater,container,false)
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

    }
    fun chargeDataRV(){
       lifecycleScope.launch(Dispatchers.IO){
           val rvAdapter=MarvelAdapter(MarvelHeroLogic().getAllHero("spider",10)){sendMarvelItem(it)}
           withContext(Dispatchers.Main){
               with(binding.rvMarvel){
                   this.adapter=rvAdapter
                   this.layoutManager=LinearLayoutManager(
                       requireActivity(),
                       LinearLayoutManager.VERTICAL,
                       false

                   )
               }
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