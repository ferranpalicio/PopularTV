package com.pal.core.di

import com.pal.core.di.modules.NetworkModule
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])// todo add ThemeModule
interface CoreComponent {

    fun okHttpClient(): OkHttpClient
    //todo fun themeUtils(): ThemeUtils

    @Component.Factory
    interface Factory {
        fun create(): CoreComponent
    }

}