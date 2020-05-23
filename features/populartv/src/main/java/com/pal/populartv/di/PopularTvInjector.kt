package com.pal.populartv.di

import com.pal.populartv.ui.MainActivity

interface PopularTvInjector {
    fun inject(activity: MainActivity)
}