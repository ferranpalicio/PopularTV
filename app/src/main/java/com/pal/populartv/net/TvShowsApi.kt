package com.pal.populartv.net

import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowsApi {
    @GET("tv/popular")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<WrapperResponse<TvShowDto>>
}