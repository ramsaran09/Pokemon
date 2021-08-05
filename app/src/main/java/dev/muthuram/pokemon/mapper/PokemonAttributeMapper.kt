package dev.muthuram.pokemon.mapper

import dev.muthuram.pokemon.constants.ERROR_SERVER
import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.handler.LocalException
import dev.muthuram.pokemon.model.AttributesModel
import retrofit2.Response

class PokemonAttributeMapper {
    companion object{
        fun map(attributesModel: Response<AttributesModel>) : CustomResponse<AttributesModel, LocalException> {
            return if (attributesModel.isSuccessful && attributesModel.code() == 200) {
                CustomResponse.Success(
                    AttributesModel(
                        attributesModel.body()?.id,
                        attributesModel.body()?.name,
                        attributesModel.body()?.base_experience,
                        attributesModel.body()?.sprites
                    )
                )
            } else CustomResponse.Failure(LocalException(ERROR_SERVER))
        }
    }
}