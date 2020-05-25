package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pal.core.di.common.CoroutineContextProvider
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TvShowsViewModel @Inject constructor(
    private val repository: TvShowsRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(){

    private val tvShowsMutableLiveData: MutableLiveData<com.pal.populartv.ui.ScreenState> = MutableLiveData()
    val tvShowsLiveData: LiveData<com.pal.populartv.ui.ScreenState>
        get() = tvShowsMutableLiveData

    private val tvShowsList = mutableListOf<TvShow>()

    internal fun getTvShows() = viewModelScope.launch {
        tvShowsMutableLiveData.value = com.pal.populartv.ui.ScreenState.Loading
        withContext(coroutineContextProvider.io) {
            val requestData = repository.getTvShows()
            updateView(requestData)
        }
    }

    private suspend fun updateView(result: Result<List<TvShow>>) {
        withContext(coroutineContextProvider.main) {
            if (result.isSuccess) {
                tvShowsList.addAll(result.getOrDefault(emptyList()))
                tvShowsMutableLiveData.value = com.pal.populartv.ui.ScreenState.Success(tvShowsList)
            } else {
                tvShowsMutableLiveData.value = com.pal.populartv.ui.ScreenState.Error(result.exceptionOrNull()?.message ?: "oh boy")
            }
        }
    }
}
