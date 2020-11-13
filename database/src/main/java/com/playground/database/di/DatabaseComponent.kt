package com.playground.database.di

import android.content.Context
import com.playground.database.AppDatabase
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent {

    fun database(): AppDatabase

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): DatabaseComponent
    }
}