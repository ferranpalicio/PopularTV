package com.pal.populartv.data.net

import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.data.net.dto.WrapperResponse
import com.pal.populartv.data.net.dto.toValueObject
import com.pal.populartv.domain.DataProvider
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
): DataProvider<Result<List<TvShow>>> {

    private var page: Int = 0

    override suspend fun requestData(callback: (result: Result<List<TvShow>>) -> Unit) {

        page++
        try {
            val response: Response<WrapperResponse<TvShowDto>> = tvShowsApi.getPopularTvShowsAsync(ApiConstants.API_KEY, page)
            if (response.isSuccessful) {
                response.body()?.also { wrapperResponse ->
                    callback(Result.success(wrapperResponse.data.map { it.toValueObject() }))
                }
            } else {
                val message = response.errorBody()?.string() ?: "Something went wrong"
                callback(Result.failure(IOException(message)))
            }
        } catch (e : Exception) {
            callback(Result.failure(e))
        }
    }
}