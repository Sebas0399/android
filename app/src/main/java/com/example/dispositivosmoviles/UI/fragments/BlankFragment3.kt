package com.example.dispositivosmoviles.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentBlank2Binding
import com.example.dispositivosmoviles.databinding.FragmentBlank3Binding


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment3 : Fragment() {


    private lateinit var binding: FragmentBlank3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBlank3Binding.inflate(layoutInflater,container,false)
        binding.webView.loadUrl("https://www.facebook.com/")
        return binding.root
    }


}