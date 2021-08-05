package dev.muthuram.pokemon.model

import android.os.Bundle




data class NavigationModel(
    val clazz: Class<*>,
    val finishCurrent: Boolean = false,
    val extras: Bundle? = null
)