package com.pal.populartv.di


import com.pal.populartv.BuildConfig
import com.pal.populartv.net.ApiConstants
import com.pal.populartv.net.TvShowsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

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
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    @Provides
    fun getApi(retrofit: Retrofit): TvShowsApi = retrofit.create(TvShowsApi::class.java)

}