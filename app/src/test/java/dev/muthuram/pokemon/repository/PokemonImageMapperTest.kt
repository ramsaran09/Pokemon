package dev.muthuram.pokemon.repository

import dev.muthuram.pokemon.constants.TEST_BASE_URL
import dev.muthuram.pokemon.data.MockServiceApi
import dev.muthuram.pokemon.data.MockServiceApiForFailScenario
import dev.muthuram.pokemon.data.PokemonImageTestModel
import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.mapper.PokemonImageMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonImageMapperTest {

    @Test
    fun image_map_return_success_response() =  runBlockingTest {
        val successResponse = Response.success(PokemonImageTestModel.getData())
        val serviceApi by lazy { MockServiceApi() }
        val repository = PokemonImageRepository(serviceApi)
        val response = repository.getPokemonImageDataFromServer(TEST_BASE_URL) as CustomResponse.Success
        val responseFromServer = serviceApi.getPokemon(TEST_BASE_URL)
        val expectedResponse = PokemonImageMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        Assert.assertEquals(successResponse.code(), responseFromServer.code())
        Assert.assertEquals(response, expectedResponse)
    }

    @Test
    fun image_map_return_failure_response() = runBlockingTest {
        val serviceApi by lazy { MockServiceApiForFailScenario() }
        val repository = PokemonImageRepository(serviceApi)
        val response = repository.getPokemonImageDataFromServer(TEST_BASE_URL) as CustomResponse.Failure
        val responseFromServer = serviceApi.getPokemon(TEST_BASE_URL)
        val expectedResponse = PokemonImageMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        Assert.assertEquals(responseFromServer.code(),400)
        Assert.assertEquals(response,expectedResponse)
    }
}