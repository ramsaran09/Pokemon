package dev.muthuram.pokemon.data

import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PokemonModel
import dev.muthuram.pokemon.service.ServiceApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class MockServiceApiForFailScenario : ServiceApi{
    override suspend fun getPokemon(url: String?): Response<PokemonModel> =
        Response.error(400,
            "".toByteArray().toResponseBody("application/json; charset=utf-8".toMediaType())
        )

    override suspend fun getPokemonAttributes(url: String?): Response<AttributesModel> =
        Response.error(400,
            "".toByteArray().toResponseBody("application/json; charset=utf-8".toMediaType())
        )
}