package dev.muthuram.pokemon.repository

import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.handler.LocalException
import dev.muthuram.pokemon.mapper.PokemonAttributeMapper
import dev.muthuram.pokemon.mapper.PokemonImageMapper
import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PokemonModel
import dev.muthuram.pokemon.service.ServiceApi

class PokemonImageRepository(
    private val serviceApi: ServiceApi
) {

    suspend fun getPokemonImageDataFromServer(url : String) : CustomResponse<PokemonModel,LocalException> {
        val response = serviceApi.getPokemon(url)
        return PokemonImageMapper.map(response)
    }

    suspend fun getPokemonAttributesFromServer(url : String) : CustomResponse<AttributesModel,LocalException>{
        val response = serviceApi.getPokemonAttributes(url)
        return PokemonAttributeMapper.map(response)
    }
}