package dev.muthuram.pokemon.mapper

import dev.muthuram.pokemon.constants.ERROR_SERVER
import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.handler.LocalException
import dev.muthuram.pokemon.model.PokemonImageModel
import dev.muthuram.pokemon.model.PokemonModel
import retrofit2.Response

class PokemonImageMapper {
    companion object {
        fun map(pokemonModel: Response<PokemonModel>) : CustomResponse<PokemonModel,LocalException> {
            return if (pokemonModel.isSuccessful && pokemonModel.code() == 200) {
                CustomResponse.Success(
                    PokemonModel(
                        pokemonModel.body()?.count,
                        pokemonModel.body()?.next,
                        pokemonModel.body()?.previous,
                        pokemonModel.body()?.results?.map { imageModel ->
                            PokemonImageModel(
                                imageModel.name,
                                imageModel.url,
                            )
                        } ?: arrayListOf()
                    )
                )
            } else CustomResponse.Failure(LocalException(ERROR_SERVER))
        }
    }
}