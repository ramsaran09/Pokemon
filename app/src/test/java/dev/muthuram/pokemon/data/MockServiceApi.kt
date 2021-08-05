package dev.muthuram.pokemon.data

import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PokemonModel
import dev.muthuram.pokemon.service.ServiceApi
import retrofit2.Response

class MockServiceApi : ServiceApi {
    override suspend fun getPokemon(url: String?): Response<PokemonModel> {
        return Response.success(PokemonImageTestModel.getData())
    }

    override suspend fun getPokemonAttributes(url: String?): Response<AttributesModel> {
        return Response.success(PokemonAttributeTestModel.getData())
    }
}