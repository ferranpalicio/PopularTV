package com.pal.core.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.pal.core.data.AppSettingsImpl
import com.pal.core.domain.AppSettings
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    fun getSharedPreferences(applicationContext: Context): SharedPreferences = applicationContext.getSharedPreferences(
        AppSettings.PREF_NAME, Context.MODE_PRIVATE
    )

    @Provides
    fun provideAppSettings(appSettingsImpl: AppSettingsImpl): AppSettings = appSettingsImpl
}
