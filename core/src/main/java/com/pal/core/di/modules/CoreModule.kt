package com.pal.core.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    fun getSharedPreferences(applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(
            "app_settings", Context.MODE_PRIVATE
        )

    /*@Provides
    fun provideAppSettings(appSettingsImpl: AppSettingsImpl): AppSettings =
        appSettingsImpl*/
}
