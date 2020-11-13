package com.pal.populartv.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.playground.database.AppDatabase
import com.pal.populartv.data.TvShowsRepositoryImpl
import com.playground.database.dao.TvShowDao
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.data.net.TvShowsApi
import com.pal.populartv.domain.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class PopularTvModule {
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    fun getApi(retrofit: Retrofit): TvShowsApi = retrofit.create(TvShowsApi::class.java)

    //repo
    @Provides
    fun provideRepository(repositoryImpl: TvShowsRepositoryImpl): TvShowsRepository = repositoryImpl

    @Provides
    fun getTvShowDao(appDatabase: AppDatabase): TvShowDao = appDatabase.tvShowDao()

}