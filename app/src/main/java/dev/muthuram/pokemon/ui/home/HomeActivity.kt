package dev.muthuram.pokemon.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.muthuram.pokemon.R
import dev.muthuram.pokemon.adapter.PokemonImageAdapter
import dev.muthuram.pokemon.enums.ListConfig
import dev.muthuram.pokemon.helper.observeLiveData
import dev.muthuram.pokemon.model.AttributesModel
import dev.muthuram.pokemon.model.DoNothing
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val homeViewModel : HomeViewModel by inject()
    private val pokemonImageAdapter by lazy { PokemonImageAdapter(this) }
    private val linearLayoutManager by lazy { LinearLayoutManager(this@HomeActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
    }

    private fun setUpUi() {
        setupAdapter()
        homeViewModel.error.observeLiveData(this, ::handleError)
        homeViewModel.loader.observeLiveData(this, ::handleLoaderVisibility)
        homeViewModel.listConfig.observeLiveData(this, ::updateListConfiguration)
        homeViewModel.pokemonAttributeData.observeLiveData(this, setAdapterData)
    }

    private fun setupAdapter() {
        uiRvPokemonImages.apply {
            layoutManager = linearLayoutManager
            adapter = pokemonImageAdapter
            addOnScrollListener(ordersScrollListener)
        }
    }

    private val ordersScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            homeViewModel.loadMoreItems(
                linearLayoutManager.childCount,
                linearLayoutManager.itemCount,
                linearLayoutManager.findFirstVisibleItemPosition()
            )
        }
    }

    private val setAdapterData: (Pair<List<AttributesModel>, Boolean>) -> Unit = { (items, footer) ->
        Log.d("attribute",items.toString())
            pokemonImageAdapter.addAllWithFooter(items, footer)
        }

    private fun updateListConfiguration (listConfig : ListConfig) {
        when (listConfig) {
            ListConfig.REMOVE_FOOTER -> pokemonImageAdapter.removeFooter()
            else -> DoNothing
        }
    }

    private fun handleError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun handleLoaderVisibility(isLoading: Boolean) {
        uiAnimationLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}