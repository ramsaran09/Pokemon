package dev.muthuram.pokemon.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.muthuram.pokemon.model.NavigationModel
import dev.muthuram.pokemon.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val navigateLd = MutableLiveData<NavigationModel>()

    val navigate: LiveData<NavigationModel> = navigateLd

    init {
        viewModelScope.launch {
            delay(2500)
            navigateLd.value = NavigationModel(
                HomeActivity::class.java,
                finishCurrent = true
            )
        }
    }
}