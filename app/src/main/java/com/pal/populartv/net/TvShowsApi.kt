package com.pal.populartv.net

import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowsApi {
    @GET("tv/popular")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("page") page: Int): Deferred<Response<WrapperResponse<TvShowDto>>>
}