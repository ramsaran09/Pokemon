package dev.muthuram.pokemon.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.muthuram.pokemon.R
import dev.muthuram.pokemon.helper.observeLiveData
import dev.muthuram.pokemon.helper.startActivity
import dev.muthuram.pokemon.model.NavigationModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpUi()
    }

    private fun setUpUi() {
        splashViewModel.navigate.observeLiveData(this, ::navigate)
    }

    private fun navigate(navigationModel: NavigationModel) {
        startActivity(navigationModel)
    }
}