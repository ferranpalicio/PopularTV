package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import com.pal.core.di.common.AsyncResult
import kotlinx.coroutines.launch
import javax.inject.Inject


class TvShowsViewModel @Inject constructor(
    private val repository: TvShowsRepository
) : ViewModel() {

    private val _tvShowsLiveData: MutableLiveData<AsyncResult<List<TvShow>>> = MutableLiveData()
    val tvShowsLiveData: LiveData<AsyncResult<List<TvShow>>>
        get() = _tvShowsLiveData

    private val tvShowsList = mutableListOf<TvShow>()

    fun getTvShows() = viewModelScope.launch {
        _tvShowsLiveData.value = AsyncResult.Loading
        updateView(repository.getTvShows())
    }

    private fun updateView(result: AsyncResult<List<TvShow>>) {
        when (result) {
            is AsyncResult.Success -> {
                tvShowsList.addAll(result.data)
                _tvShowsLiveData.value = AsyncResult.Success(tvShowsList)
            }
            else -> {
                _tvShowsLiveData.value = result
            }
        }
    }
}
