package com.pal.playgorund.data.net

import com.pal.playgorund.data.DataProvider
import com.pal.playgorund.data.net.dto.TvShowDto
import com.pal.playgorund.data.net.dto.WrapperResponse
import dagger.Reusable
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@Reusable
class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
) : DataProvider<List<TvShowDto>> {

    @Throws(Exception::class)
    override suspend fun requestData(page: Int): List<TvShowDto> {

        try {
            val response: Response<WrapperResponse<TvShowDto>> =
                tvShowsApi.getPopularTvShowsAsync(ApiConstants.API_KEY, page)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    return response.body()!!.data
                } else {
                    throw Exception("Error parsing body")
                }
            } else {
                val message = response.errorBody()?.string() ?: "Something went wrong"
                throw IOException(message)
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}