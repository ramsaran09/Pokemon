package dev.muthuram.pokemon.helper

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import dev.muthuram.pokemon.R
import dev.muthuram.pokemon.model.NavigationModel


fun Activity.insertImageIntoView(
    image: String?,
    target: ImageView,
    @DrawableRes placeHolder: Int = R.drawable.ic_image_placeholder
) {
    Glide.with(this)
        .load(image.defaultValue())
        .placeholder(placeHolder)
        .into(target)
}

fun Activity.startActivity(navigationModel: NavigationModel) {
    val intent = Intent(this, navigationModel.clazz)
    if (navigationModel.extras != null)
        intent.putExtras(navigationModel.extras)
    startActivity(intent)
    if (navigationModel.finishCurrent)
        finish()
}

