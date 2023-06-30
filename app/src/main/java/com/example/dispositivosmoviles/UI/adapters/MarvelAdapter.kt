package com.example.dispositivosmoviles.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelHero
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(private  val items:List<MarvelHero>, private var fnClick:(MarvelHero)->Unit):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View):RecyclerView.ViewHolder(view){
        private var binding:MarvelCharactersBinding=MarvelCharactersBinding.bind(view)
        fun render(item: MarvelHero, fnClick: (MarvelHero) -> Unit){
            Picasso.get().load(item.foto).into(binding.imgMarvel)
            binding.txtTituloMarvel.text=item.nombre
            binding.txtComicMarvel.text=item.comic
           /* binding.tarjeta.setOnClickListener{
                Snackbar.make(binding.imgMarvel,item.nombre,Snackbar.LENGTH_SHORT).show()
            }*/
            itemView.setOnClickListener{
                fnClick(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelAdapter.MarvelViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters,parent,false))
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position],fnClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}