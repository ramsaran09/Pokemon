package dev.muthuram.pokemon.data

import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.Sprites

object PokemonAttributeTestModel  {

    fun getData() = AttributesModel(
        id = 1,
        name = "jon snow",
        base_experience =  1000,
        sprites = (
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/yellow/back/gray/9.png")
                )
    )
}