package com.pal.playgorund.di


import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pal.populartv.data.TvShowsRepositoryImpl
import com.pal.populartv.data.local.AppDatabase
import com.pal.populartv.data.local.AppSettingsImpl
import com.pal.populartv.data.local.TvShowDao
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.data.net.TvShowsApi
import com.pal.populartv.domain.AppSettings
import com.pal.populartv.domain.repository.TvShowsRepository
import com.pal.populartv.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ContextModule {

    @Provides
    fun getSharedPreferences(applicationContext: Context): SharedPreferences = applicationContext.getSharedPreferences(
        AppSettings.PREF_NAME, Context.MODE_PRIVATE
    )

    //todo getResourcesMethod?

    //todo remove all this methods (move it to correspondent modules/components)
    @Provides
    fun getCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    fun getApi(retrofit: Retrofit): TvShowsApi = retrofit.create(TvShowsApi::class.java)

    //local
    @Provides
    fun getDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database.db").build()

    @Provides
    fun getTvShowDao(appDatabase: AppDatabase): TvShowDao = appDatabase.tvShowDao()

    //repo
    @Provides
    fun provideRepository(repositoryImpl: TvShowsRepositoryImpl): TvShowsRepository = repositoryImpl

    @Provides
    fun provideAppSettings(appSettingsImpl: AppSettingsImpl): AppSettings = appSettingsImpl
}