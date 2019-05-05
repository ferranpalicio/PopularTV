package com.pal.populartv.net

import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.dto.TvShowDto
import com.pal.populartv.net.dto.WrapperResponse
import com.pal.populartv.net.dto.toValueObject
import com.pal.populartv.provider.DataProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class NetworkDataProvider @Inject constructor(
    private val tvShowsApi: TvShowsApi
): DataProvider<TvShow.State>{

    private var page: Int = 0

    override fun requestData(callback: (item: TvShow.State) -> Unit) {
        callback(TvShow.State.Loading)

        page = page.inc()
        tvShowsApi.getPopularTvShows(ApiConstants.API_KEY, page).enqueue(object : Callback<WrapperResponse<TvShowDto>> {
            override fun onFailure(call: Call<WrapperResponse<TvShowDto>>, t: Throwable) {
                callback(TvShow.State.Error(t.localizedMessage))
            }

            override fun onResponse(
                call: Call<WrapperResponse<TvShowDto>>,
                response: Response<WrapperResponse<TvShowDto>>
            ) {
                //todo handle response codes
                response.body()?.also {
                    val items: MutableList<TvShow> = mutableListOf()
                    it.data.forEach{ tvShowDto: TvShowDto -> items.add(tvShowDto.toValueObject()) }
                    callback(TvShow.State.Success(items))
                }
            }
        })
    }
}