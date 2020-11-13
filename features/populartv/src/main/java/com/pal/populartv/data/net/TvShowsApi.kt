package com.pal.populartv.data.net

import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.WrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowsApi {
    @GET("tv/popular")
    suspend fun getPopularTvShowsAsync(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<com.pal.populartv.data.net.dto.WrapperResponse<com.pal.populartv.data.net.dto.TvShowDto>>
}