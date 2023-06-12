package com.example.dispositivosmoviles.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentBlank2Binding
import com.example.dispositivosmoviles.databinding.FragmentBlankBinding


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {
    private lateinit var binding: FragmentBlank2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBlank2Binding.inflate(layoutInflater,container,false)
        return binding.root
    }


}