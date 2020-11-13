package com.playground.database.di

import android.content.Context
import androidx.room.Room
import com.playground.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun getDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database.db").build()
}