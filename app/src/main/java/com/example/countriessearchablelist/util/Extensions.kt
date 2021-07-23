package com.example.countriessearchablelist.util

import android.widget.ImageView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import coil.size.ViewSizeResolver
import java.util.*

fun ImageView.loadSvgOrOthers(myUrl: String?) {
    myUrl?.let {
        if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
            val imageLoader = ImageLoader.Builder(this.context)
                .componentRegistry {
                    add(SvgDecoder(this@loadSvgOrOthers.context))
                }
                .build()
            val request = LoadRequest.Builder(this.context)
                .data(it)
                .target(this)
                .apply {

                }
                .size(
                    ViewSizeResolver(this))
                .build()
            imageLoader.execute(request)
        } else {
            this.load(it)
        }
    }
}