package com.pal.populartv.data.net

import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.PagedWrapperResponse
import com.pal.populartv.data.net.dto.TvShowDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TvShowsApi {
    @GET("tv/popular")
    suspend fun getPopularTvShowsAsync(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PagedWrapperResponse<TvShowDto>>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetailInfo(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<TvShowDetailDto>
}