package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import com.pal.core.common.AsyncResult
import com.pal.populartv.data.net.ApiConstants.Companion.INITIAL_PAGE
import kotlinx.coroutines.launch
import javax.inject.Inject


class TvShowsViewModel @Inject constructor(
    private val repository: TvShowsRepository
) : ViewModel() {

    private val _tvShowsLiveData: MutableLiveData<AsyncResult<List<TvShow>>> = MutableLiveData()
    val tvShowsLiveData: LiveData<AsyncResult<List<TvShow>>>
        get() = _tvShowsLiveData

    private val tvShowsList = mutableListOf<TvShow>()
    private var page = INITIAL_PAGE

    fun getTvShows() = viewModelScope.launch {
        _tvShowsLiveData.value = AsyncResult.Loading
        updateView(repository.getTvShows(page))
    }

    private fun updateView(result: AsyncResult<Pair<List<TvShow>, Int>>) {
        when (result) {
            is AsyncResult.Success -> {
                val pageLoaded = result.data.second
                val elements = result.data.first
                if (pageLoaded < page) {
                    page = pageLoaded
                    tvShowsList.clear()
                    tvShowsList.addAll(elements)
                    _tvShowsLiveData.value = AsyncResult.Success(tvShowsList)
                } else {
                    tvShowsList.addAll(elements)
                    _tvShowsLiveData.value = AsyncResult.Success(tvShowsList)
                }
                page++
            }
            is AsyncResult.Error -> {
                _tvShowsLiveData.value = result
            }
        }
    }
}
