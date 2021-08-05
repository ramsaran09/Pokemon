package dev.muthuram.pokemon.repository

import dev.muthuram.pokemon.constants.TEST_BASE_URL
import dev.muthuram.pokemon.data.MockServiceApi
import dev.muthuram.pokemon.data.MockServiceApiForFailScenario
import dev.muthuram.pokemon.data.PokemonAttributeTestModel
import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.mapper.PokemonAttributeMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonAttributeMapperTest {

    @Test
    fun attribute_map_return_success_response() =  runBlockingTest {
        val successResponse = Response.success(PokemonAttributeTestModel.getData())
        val serviceApi by lazy { MockServiceApi() }
        val repository = PokemonImageRepository(serviceApi)
        val response = repository.getPokemonAttributesFromServer(TEST_BASE_URL) as CustomResponse.Success
        val responseFromServer = serviceApi.getPokemonAttributes(TEST_BASE_URL)
        val expectedResponse = PokemonAttributeMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        assertEquals(successResponse.code(), responseFromServer.code())
        assertEquals(response, expectedResponse)
    }

    @Test
    fun attribute_map_return_failure_response() = runBlockingTest {
        val serviceApi by lazy { MockServiceApiForFailScenario() }
        val repository = PokemonImageRepository(serviceApi)
        val response = repository.getPokemonAttributesFromServer(TEST_BASE_URL) as CustomResponse.Failure
        val responseFromServer = serviceApi.getPokemonAttributes(TEST_BASE_URL)
        val expectedResponse = PokemonAttributeMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        assertEquals(responseFromServer.code(),400)
        assertEquals(response,expectedResponse)
    }
}