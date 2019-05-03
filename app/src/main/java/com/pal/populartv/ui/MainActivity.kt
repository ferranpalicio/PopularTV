package com.pal.populartv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pal.populartv.R
import com.pal.populartv.di.AppComponent
import com.pal.populartv.di.DaggerAppComponent
import com.pal.populartv.net.ApiConstants
import com.pal.populartv.net.TvShowsApi
import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    @Inject lateinit var tvShowsApi: TvShowsApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        setContentView(R.layout.activity_main)

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
