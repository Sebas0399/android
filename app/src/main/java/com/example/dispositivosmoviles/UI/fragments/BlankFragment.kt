package com.example.dispositivosmoviles.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.UI.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.UI.adapters.MarvelAdapter
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding
import com.example.dispositivosmoviles.logic.ListItems
import com.example.dispositivosmoviles.logic.data.MarvelHero

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
        chargeDataRV()
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing=false
        }
    }
    fun chargeDataRV(){
        val rvAdapter=MarvelAdapter(
            ListItems().returnMarvelChars()
        ){
            sendMarvelItem(it)
        }
        val rvMarvel=binding.rvMarvel
        with(rvMarvel){
            rvMarvel.adapter=rvAdapter
            rvMarvel.layoutManager=LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,false
            )
        }
    }
    fun sendMarvelItem(item:MarvelHero){
        val i= Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name",item)
        i.putExtra("comic",item)
        i.putExtra("imagen",item)
        startActivity(i)
    }

}