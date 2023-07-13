package com.example.dispositivosmoviles.UI.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.logic.data.MarvelHero
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding

import com.squareup.picasso.Picasso

class MarvelAdapter(


    private var fnClick: (MarvelHero) -> Unit,
    private var fnSave: (MarvelHero) -> Boolean
) :

    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    var items: List<MarvelHero> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelHero,
            fnClick: (MarvelHero) -> Unit,
            fnSave: (MarvelHero) -> Boolean
        ) {
            binding.txtTituloMarvel.text = item.nombre
            binding.txtComicMarvel.text = item.comic
            Picasso.get().load(item.foto).into(binding.imgMarvel)




            itemView.setOnClickListener {
                fnClick(item)
                }
            //boton vista marvel chars
          //  binding.b{
            //    fnSave(item)
            //}


        }
    }




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick,fnSave)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<MarvelHero>) {
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()

    }

    fun replaceListAdapter(newItems: List<MarvelHero>) {
        this.items = newItems
        notifyDataSetChanged()

    }

}