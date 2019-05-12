package com.pal.populartv.net

import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import com.pal.populartv.net.dto.toValueObject
import com.pal.populartv.provider.DataProvider
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
): DataProvider<TvShow.State>{

    private var page: Int = 0

    override suspend fun requestData(callback: (item: TvShow.State) -> Unit) {
        callback(TvShow.State.Loading)

        page = page.inc()
        try {
            val response: Response<WrapperResponse<TvShowDto>> = tvShowsApi.getPopularTvShows(ApiConstants.API_KEY, page).await()
            if (response.isSuccessful) {
                response.body()?.also {
                    val items: MutableList<TvShow> = mutableListOf()
                    it.data.forEach{ tvShowDto: TvShowDto -> items.add(tvShowDto.toValueObject()) }
                    callback(TvShow.State.Success(items))
                }
            } else {
                val message = response.errorBody()?.string() ?: "Something went wrong"
                callback(TvShow.State.Error(message))
            }
        } catch (e : Exception) {
            callback(TvShow.State.Error(e.localizedMessage))
        }
    }
}