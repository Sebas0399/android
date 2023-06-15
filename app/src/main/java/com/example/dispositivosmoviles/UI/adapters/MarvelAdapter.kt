package com.example.dispositivosmoviles.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.MarvelHero
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding

class MarvelAdapter(private  val items:List<MarvelHero>):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View):RecyclerView.ViewHolder(view){
        private var binding:MarvelCharactersBinding=MarvelCharactersBinding.bind(view)
        fun render(item:MarvelHero){
            binding.txtTituloMarvel.text=item.nombre
            binding.txtComicMarvel.text=item.comic
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelAdapter.MarvelViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters,parent,false))
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}