package com.pal.populartv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.NetworkDataProvider
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TvShowsViewModel @Inject constructor(
    private val networkDataProvider: NetworkDataProvider
): ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val tvShowsMutableLiveData: MutableLiveData<TvShow.State> = MutableLiveData()
    val tvShowsLiveData: LiveData<TvShow.State>
        get() = tvShowsMutableLiveData

    init {
        getTvShows()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun getTvShows() = launch {
        withContext(Dispatchers.IO) {
            networkDataProvider.requestData { updateView(it) }
        }
    }

    private fun updateView(tvShowState: TvShow.State) = launch {
        withContext(Dispatchers.Main) {
            tvShowsMutableLiveData.value = tvShowState
        }
    }
}
