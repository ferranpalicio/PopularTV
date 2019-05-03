package com.pal.populartv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pal.populartv.BuildConfig
import com.pal.populartv.R
import com.pal.populartv.net.ApiConstants
import com.pal.populartv.net.TvShowsApi
import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }).build()

        val retrofit = Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

        val tvShowsApi = retrofit.create(TvShowsApi::class.java)

        tvShowsApi.getPopularTvShows(ApiConstants.API_KEY, 1).enqueue(object : Callback<WrapperResponse<TvShowDto>> {
            override fun onFailure(call: Call<WrapperResponse<TvShowDto>>, t: Throwable) {
                Log.e("tag", "this is an error")
            }

            override fun onResponse(
                call: Call<WrapperResponse<TvShowDto>>,
                response: Response<WrapperResponse<TvShowDto>>
            ) {
                Log.i("tag", "num items: ${response.body()?.data?.size}")
            }
        })
    }
}
