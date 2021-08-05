package dev.muthuram.pokemon.service


import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ServiceApi {

    @GET
    suspend fun getPokemon(@Url url: String?) : Response<PokemonModel>

    @GET
    suspend fun getPokemonAttributes(@Url url: String?) : Response<AttributesModel>
}