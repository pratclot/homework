package ch.protonmail.android.protonmailtest.util

import android.widget.ImageView
import ch.protonmail.android.protonmailtest.R
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest

fun ImageView.loadWithPlaceholder(
    data: Any?,
    imageLoader: ImageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = load(data, imageLoader) {
    placeholder(R.drawable.item_task_image_default)
    error(R.drawable.item_task_image_default)
    builder()
}
