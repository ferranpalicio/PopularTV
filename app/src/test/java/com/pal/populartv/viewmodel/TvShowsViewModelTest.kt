package com.pal.populartv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.NetworkDataProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: TvShowsViewModel
    private val networkDataProvider: NetworkDataProvider = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TvShowsViewModel(networkDataProvider)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun update_live_data_for_successful_state() = testDispatcher.runBlockingTest {

        val listTvShows: List<TvShow> = createFakeList()
        val successfulState: TvShow.State = TvShow.State.Success(listTvShows)

        whenever(networkDataProvider.requestData(any())).then { invocation ->
            (invocation.arguments[0] as (TvShow.State) -> Unit).invoke(successfulState)
        }

        val observer: Observer<TvShow.State> = mock()
        viewModel.tvShowsLiveData.observeForever(observer)
        viewModel.getTvShows()

        verify(networkDataProvider, atLeastOnce()).requestData(any())

        //todo
        //assertEquals(viewModel.tvShowsLiveData.value, successfulState)
    }

//    @Test
//    fun update_live_data_for_error_state() = testDispatcher.runBlockingTest {
//
//        val errorState: TvShow.State = TvShow.State.Error("error")
//
//        whenever(networkDataProvider.requestData(any())).then { invocation ->
//            (invocation.arguments[0] as (TvShow.State) -> Unit).invoke(errorState)
//        }
//
//        val observer: Observer<TvShow.State> = mock()
//        viewModel.tvShowsLiveData.observeForever(observer)
//        viewModel.getTvShows()
//
//        verify(networkDataProvider, atLeastOnce()).requestData(any())
//        assertEquals(viewModel.tvShowsLiveData.value, errorState)
//    }

    private fun createFakeList(): List<TvShow> {
        return mutableListOf(TvShow(1, "name1", "image1", "score1"), TvShow(2, "name2", "image2", "score2"))
    }
}