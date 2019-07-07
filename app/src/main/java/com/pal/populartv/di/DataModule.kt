package com.pal.populartv.di


import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pal.populartv.BuildConfig
import com.pal.populartv.data.TvShowRepositoryImpl
import com.pal.populartv.data.local.AppDatabase
import com.pal.populartv.data.local.AppSettingsImpl
import com.pal.populartv.data.local.TvShowDao
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.data.net.TvShowsApi
import com.pal.populartv.domain.AppSettings
import com.pal.populartv.domain.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    /*
    For the moment, there's no need to mark this with @Reusable neither @Singleton, since there's only one screen, and
    only one instance of each dependency it's been created and required. Once I add another viewmodel, annotation should be added
    */


    //network
    @Provides
    fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }
    }).build()

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
    fun getDatabase(applicationContext: Context) : AppDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database.db").build()

    @Provides
    fun getTvShowDao(appDatabase: AppDatabase) : TvShowDao = appDatabase.tvShowDao()

    //repo
    @Provides
    fun provideRepository(repositoryImpl: TvShowRepositoryImpl): TvShowsRepository = repositoryImpl

    @Provides
    fun provideAppSettings(appSettingsImpl: AppSettingsImpl): AppSettings = appSettingsImpl

    @Provides
    fun getSaredPreferences(applicationContext: Context) : SharedPreferences = applicationContext.getSharedPreferences(
        AppSettings.PREF_NAME, Context.MODE_PRIVATE)
}