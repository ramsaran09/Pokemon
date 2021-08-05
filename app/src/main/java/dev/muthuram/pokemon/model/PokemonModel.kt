package dev.muthuram.pokemon.model

data class PokemonModel(
    val count : Int?,
    val next : String?,
    val previous : String?,
    val results : List<PokemonImageModel>? = ArrayList(),
)

data class PokemonImageModel(
    val name : String?,
    val url : String?,
)

data class PaginationModel(
    val limit: Int = 10,
    var isLoading: Boolean = false,
    var isLastPage: Boolean = false,
    var offset : Int = 0
)

data class AttributesModel(
    val id : Int?,
    val name : String?,
    val base_experience : Int?,
    val sprites : Sprites?,
)

data class Sprites(
    val back_default : String?,
)