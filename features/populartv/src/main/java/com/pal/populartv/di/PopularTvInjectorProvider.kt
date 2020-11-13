package com.pal.populartv.di

import com.pal.populartv.di.PopularTVComponent

interface PopularTvInjectorProvider {
    fun popularTvInjector(): PopularTVComponent
}