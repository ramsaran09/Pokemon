package dev.muthuram.pokemon.di

import dev.muthuram.pokemon.repository.PokemonImageRepository
import dev.muthuram.pokemon.service.ApiProvider
import dev.muthuram.pokemon.ui.home.HomeViewModel
import dev.muthuram.pokemon.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {

    private val viewModelModules = module {
        viewModel { HomeViewModel(get()) }
        viewModel { SplashViewModel() }
    }

    private val repoModules = module {
        single { PokemonImageRepository(get()) }
    }

    private val commonModules = module {
        single { ApiProvider.client }
    }

    fun appModules() = viewModelModules + repoModules + commonModules

}