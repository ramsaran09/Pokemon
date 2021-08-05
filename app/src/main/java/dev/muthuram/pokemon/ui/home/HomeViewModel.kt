package dev.muthuram.pokemon.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.muthuram.pokemon.BuildConfig
import dev.muthuram.pokemon.enums.ListConfig
import dev.muthuram.pokemon.handler.CustomResponse
import dev.muthuram.pokemon.helper.defaultValue
import dev.muthuram.pokemon.helper.ifNotNull
import dev.muthuram.pokemon.helper.setMap
import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.PaginationModel
import dev.muthuram.pokemon.model.PokemonModel
import dev.muthuram.pokemon.repository.PokemonImageRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
    private val pokemonImageRepository: PokemonImageRepository,
) : ViewModel() {

    private val errorLd = MutableLiveData<String>()
    private val loaderLd = MutableLiveData<Boolean>()
    private val pokemonAttributeDataLd = MutableLiveData<AttributesModel>()
    private val listConfigLD = MutableLiveData<ListConfig>()
    private val paginationModel = PaginationModel()

    init {
        loaderLd.value = true
        getPokemonImageData()
    }

    val error: LiveData<String> = errorLd
    val loader: LiveData<Boolean> = loaderLd
    val listConfig: LiveData<ListConfig> = listConfigLD
    private val pokemonAttributeDataApi : LiveData<AttributesModel> = pokemonAttributeDataLd

    val pokemonAttributeData : LiveData<Pair<List<AttributesModel>, Boolean>> =
        setMap(pokemonAttributeDataApi) { data, result ->
            val tree = TreeMap<Int,AttributesModel>()
            val attributeList : ArrayList<AttributesModel> = arrayListOf()
            if (data != null) {
                attributeList.add(data)
                tree[data.id?.minus(1).defaultValue()] = data
            }
            listConfigLD.value = ListConfig.REMOVE_FOOTER
            Log.d("treeList",tree.toString())
            if (attributeList.size >= paginationModel.limit) {
                result.value = attributeList to true
            }else {
                result.value = attributeList to false
                paginationModel.isLoading = false
                }
            if (paginationModel.offset == 300) {
                paginationModel.isLastPage = true
            }
        }

    private fun getPokemonImageData() {
        viewModelScope.launch {
            when (val response = getPokemon()) {
                is CustomResponse.Success -> getAttribute(response.data)
                is CustomResponse.Failure -> errorLd.value = response.error.message
            }.also { loaderLd.value = false }
        }
    }

    private fun getImageUrl() : String {
        return BuildConfig.BASE_URL + "api/v2/pokemon?" +
                    "offset=${paginationModel.offset}" +
                    "&limit=${paginationModel.limit}"
    }

    private suspend fun getPokemon() =
        pokemonImageRepository.getPokemonImageDataFromServer(getImageUrl())

    private fun getAttribute(data: PokemonModel?) {
        data?.results.ifNotNull { pokemonList ->
            val attributeUrlList = arrayListOf<String>()
            pokemonList.forEach {
                attributeUrlList.add(it.url.defaultValue())
            }
            getPokemonAttributeData(attributeUrlList)
        }
    }

    private fun getPokemonAttributeData(urlList: ArrayList<String>) {
        viewModelScope.launch {
            coroutineScope {
                urlList.forEach { url ->
                    launch {
                        when (val response = getPokemonAttribute(url)) {
                            is CustomResponse.Success -> pokemonAttributeDataLd.value =
                                response.data
                            is CustomResponse.Failure -> errorLd.value = response.error.message
                        }
                    }
                }
            }
        }
    }

    private suspend fun getPokemonAttribute(url: String) =
        pokemonImageRepository.getPokemonAttributesFromServer(url)


    fun loadMoreItems(
        visibleCount: Int,
        totalItemCount: Int,
        firstVisibleItemPosition: Int
    ) {
        if (!paginationModel.isLoading && !paginationModel.isLastPage) {
            if ((visibleCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= paginationModel.limit
            ) {
                paginationModel.isLoading = true
                paginationModel.offset = paginationModel.offset + 10
                getPokemonImageData()
            }
        }
    }
}