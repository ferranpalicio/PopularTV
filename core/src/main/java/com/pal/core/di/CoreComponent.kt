package com.pal.core.di

import android.content.Context
import android.content.SharedPreferences
import com.pal.core.di.modules.CoreModule
import com.pal.core.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CoreModule::class])
interface CoreComponent {

    fun okHttpClient(): OkHttpClient
    fun sharedPreferences(): SharedPreferences

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): CoreComponent
    }

}