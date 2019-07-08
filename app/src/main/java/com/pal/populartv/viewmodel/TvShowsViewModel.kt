package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import com.pal.populartv.ui.ScreenState
import com.pal.populartv.utils.CoroutineContextProvider
import kotlinx.coroutines.*
import javax.inject.Inject


class TvShowsViewModel @Inject constructor(
    private val repository: TvShowsRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(){

    private val tvShowsMutableLiveData: MutableLiveData<ScreenState> = MutableLiveData()
    val tvShowsLiveData: LiveData<ScreenState>
        get() = tvShowsMutableLiveData

    private val tvShowsList = mutableListOf<TvShow>()

    internal fun getTvShows() = viewModelScope.launch {
        tvShowsMutableLiveData.value = ScreenState.Loading
        withContext(coroutineContextProvider.io) {
            val requestData = repository.getTvShows()
            updateView(requestData)
        }
    }

    private suspend fun updateView(result: Result<List<TvShow>>) {
        withContext(coroutineContextProvider.main) {
            if (result.isSuccess) {
                tvShowsList.addAll(result.getOrDefault(emptyList()))
                tvShowsMutableLiveData.value = ScreenState.Success(tvShowsList)
            } else {
                tvShowsMutableLiveData.value = ScreenState.Error(result.exceptionOrNull()?.message ?: "oh boy")
            }
        }
    }
}
