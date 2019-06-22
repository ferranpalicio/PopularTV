package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.ui.ScreenState
import com.pal.populartv.utils.DispatcherHelper
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TvShowsViewModel @Inject constructor(
    private val networkDataProvider: NetworkDataProvider
) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val tvShowsMutableLiveData: MutableLiveData<ScreenState> = MutableLiveData()
    val tvShowsLiveData: LiveData<ScreenState>
        get() = tvShowsMutableLiveData

    private val dispatcherHelper = DispatcherHelper()
    private val tvShowsList = mutableListOf<TvShow>()

    init {
        getTvShows()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    internal fun getTvShows() = launch {
        withContext(dispatcherHelper.io) {
            networkDataProvider.requestData { updateView(it)}
        }
    }

    //private fun updateView(tvShowState: ScreenState) = launch {
    private fun updateView(result: Result<List<TvShow>>) = launch {
        withContext(dispatcherHelper.main) {
            if (result.isSuccess) {
                tvShowsList.addAll(result.getOrDefault(emptyList()))
                tvShowsMutableLiveData.value = ScreenState.Success(tvShowsList)
            } else {
                tvShowsMutableLiveData.value = ScreenState.Error(result.exceptionOrNull()?.message ?: "oh boy")
            }
        }
    }
}
