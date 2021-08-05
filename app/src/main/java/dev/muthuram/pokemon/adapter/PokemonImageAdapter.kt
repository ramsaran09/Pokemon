package dev.muthuram.pokemon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.muthuram.pokemon.R
import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PokemonImageModel
import kotlinx.android.synthetic.main.model_pokemon_list.view.*

class PokemonImageAdapter(
    val contexts: Context,
) : PaginationAdapter<AttributesModel>(){

    override fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PokemonImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_pokemon_list, parent, false)
        )

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = items[position]
        with(viewHolder.itemView) {
            uiTvIdTitle.text = pokemon.name
            uiTvId.text = pokemon.id.toString()
            uiTvStatus.text = "EXP - ${pokemon.base_experience}"
            Glide.with(contexts)
                .load(pokemon.sprites?.back_default)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(uiIvPokemonIcon)
        }
    }

    inner class PokemonImageViewHolder(view: View) : RecyclerView.ViewHolder(view) { }
}