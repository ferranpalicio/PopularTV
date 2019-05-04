package com.pal.populartv.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pal.populartv.R
import com.pal.populartv.di.AppComponent
import com.pal.populartv.di.DaggerAppComponent
import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.ApiConstants
import com.pal.populartv.net.TvShowsApi
import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import com.pal.populartv.net.dto.toValueObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    @Inject
    lateinit var tvShowsApi: TvShowsApi

    private val recyclerView: RecyclerView by bind(R.id.recycler_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = TvShowsAdapter()
        }

        tvShowsApi.getPopularTvShows(ApiConstants.API_KEY, 1).enqueue(object : Callback<WrapperResponse<TvShowDto>> {
            override fun onFailure(call: Call<WrapperResponse<TvShowDto>>, t: Throwable) {
                Log.e("tag", "this is an error")
            }

            override fun onResponse(
                    call: Call<WrapperResponse<TvShowDto>>,
                    response: Response<WrapperResponse<TvShowDto>>
            ) {
                response.body()?.also {
                    val items: MutableList<TvShow> = mutableListOf()
                    it.data.forEach{ tvShowDto: TvShowDto -> items.add(tvShowDto.toValueObject()) }
                    (recyclerView.adapter as TvShowsAdapter).addItems(items)
                }
            }
        })
    }

    fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
    }
}
