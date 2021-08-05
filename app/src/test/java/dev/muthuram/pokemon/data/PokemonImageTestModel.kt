package dev.muthuram.pokemon.data

import dev.muthuram.pokemon.model.PokemonImageModel
import dev.muthuram.pokemon.model.PokemonModel

object PokemonImageTestModel {

    fun getData() = PokemonModel(
        count = 1111,
        next = "https://pokeapi.co/api/v2/pokemon?offset2%limit2",
        previous = null,
        results = arrayListOf(
            PokemonImageModel(
                name = "pokemon1",
                url = "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            PokemonImageModel(
                name = "pokemon2",
                url = "https://pokeapi.co/api/v2/pokemon/2/"
            ),
        ),
    )
}