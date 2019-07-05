package com.pal.populartv.data.net

import com.pal.populartv.data.DataProvider
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.WrapperResponse
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
) : DataProvider<List<TvShow>> {

    private var page: Int = 0

    override suspend fun requestData(): Result<List<TvShow>> {

        page++
        try {
            val response: Response<WrapperResponse<TvShowDto>> =
                tvShowsApi.getPopularTvShowsAsync(ApiConstants.API_KEY, page)
            return if (response.isSuccessful) {
                var list = listOf<TvShow>()
                response.body()?.also { wrapperResponse ->
                    list = wrapperResponse.data.map { it.toDomain() }
                }
                Result.success(list)
            } else {
                val message = response.errorBody()?.string() ?: "Something went wrong"
                Result.failure(IOException(message))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}