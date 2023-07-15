package com.example.dispositivosmoviles.UI.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.PokemonPetsBinding
import com.example.dispositivosmoviles.logic.data.PokemonPet
import com.squareup.picasso.Picasso

class PokemonAdapter(


    private var fnClick: (PokemonPet) -> Unit,
) :

    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var items: List<PokemonPet> = listOf()

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: PokemonPetsBinding=PokemonPetsBinding.bind(view)

        fun render(
            item: PokemonPet,
            fnClick: (PokemonPet) -> Unit,
        ) {
            binding.txtNombrePkm.text = item.nombre
            binding.txtTipoPkm.text = item.tipos
            Picasso.get().load(item.foto).into(binding.imgPkm)




            itemView.setOnClickListener {
                fnClick(item)
            }
            //boton vista marvel chars



        }
    }




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(
            inflater.inflate(
                R.layout.pokemon_pets,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<PokemonPet>) {
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()

    }

    fun replaceListAdapter(newItems: List<PokemonPet>) {
        this.items = newItems
        notifyDataSetChanged()

    }

}