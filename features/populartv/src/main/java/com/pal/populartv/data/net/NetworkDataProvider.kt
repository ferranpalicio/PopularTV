package com.pal.populartv.data.net

import com.pal.populartv.data.DataProvider
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.PagedWrapperResponse
import com.pal.populartv.data.net.dto.TvShowDetailDto
import dagger.Reusable
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@Reusable
class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
) : DataProvider<List<TvShowDto>, TvShowDetailDto> {

    //todo create common function to extract data

    @Throws(Exception::class)
    override suspend fun requestPagedData(page: Int): List<TvShowDto> {
//        try {
        val pagedResponse: Response<PagedWrapperResponse<TvShowDto>> =
            tvShowsApi.getPopularTvShowsAsync(ApiConstants.API_KEY, page)
        if (pagedResponse.isSuccessful) {
            return pagedResponse.body()?.data ?: throw Exception("Error parsing body")
        } else {
            val message =
                pagedResponse.errorBody()?.string() ?: "Something went wrong fetching paged data"
            throw IOException(message)
        }
        /*} catch (e: Exception) {
            throw Exception(e)
        }*/
    }

    @Throws(Exception::class)
    override suspend fun requestDetailData(id: Int): TvShowDetailDto {
        val tvShowDetailInfo = tvShowsApi.getTvShowDetailInfo(id, ApiConstants.API_KEY)
        if (tvShowDetailInfo.isSuccessful) {
            return tvShowDetailInfo.body() ?: throw Exception("Error parsing body")
        } else {
            val message = tvShowDetailInfo.errorBody()?.string()
                ?: "Something went wrong fetching detail data"
            throw IOException(message)
        }
    }

}