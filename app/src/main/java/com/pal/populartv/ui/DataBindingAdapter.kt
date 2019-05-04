package com.pal.populartv.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object DataBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url:String) {
        Picasso.with(view.context).load(url).into(view)
    }
}