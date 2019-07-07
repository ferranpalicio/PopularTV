package com.pal.populartv.data.net

import com.pal.populartv.data.DataProvider
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.WrapperResponse
import dagger.Reusable
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@Reusable
class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
) : DataProvider<List<TvShowDto>> { //todo DataProvider<List<TvShowDto>>

//    private var page: Int = 0

    @Throws(Exception::class)
    override suspend fun requestData(page: Int): List<TvShowDto> {

//        page++
        try {
            val response: Response<WrapperResponse<TvShowDto>> =
                tvShowsApi.getPopularTvShowsAsync(ApiConstants.API_KEY, page)
            if (response.isSuccessful) {
                var list = listOf<TvShowDto>()
                response.body()?.also { wrapperResponse ->
                    //list = wrapperResponse.data.map { it.toDomain() }
                    list = wrapperResponse.data
                }
                return list
            } else {
                val message = response.errorBody()?.string() ?: "Something went wrong"
                throw IOException(message)
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}