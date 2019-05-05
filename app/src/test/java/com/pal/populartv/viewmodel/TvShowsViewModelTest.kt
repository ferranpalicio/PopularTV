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
import org.mockito.ArgumentCaptor

@Suppress("UNCHECKED_CAST")
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

        val state: TvShow.State = TvShow.State.Success(createFakeList())

        whenever(networkDataProvider.requestData(any())).then { invocation ->
            (invocation.arguments[0] as (TvShow.State) -> Unit).invoke(state)
        }

        val observer: Observer<TvShow.State> = mock()
        viewModel.tvShowsLiveData.observeForever(observer)
        viewModel.getTvShows()

        val argumentCaptor = ArgumentCaptor.forClass(TvShow.State::class.java)
        argumentCaptor.run {
            verify(observer, times(1)).onChanged(capture())
            val(successState) = allValues
            assertEquals(successState, state)

        }

    }

    @Test
    fun update_live_data_for_error_state() = testDispatcher.runBlockingTest {

        val state: TvShow.State = TvShow.State.Error("error")

        whenever(networkDataProvider.requestData(any())).then { invocation ->
            (invocation.arguments[0] as (TvShow.State) -> Unit).invoke(state)
        }

        val observer: Observer<TvShow.State> = mock()
        viewModel.tvShowsLiveData.observeForever(observer)
        viewModel.getTvShows()

        val argumentCaptor = ArgumentCaptor.forClass(TvShow.State::class.java)
        argumentCaptor.run {
            verify(observer, times(1)).onChanged(capture())
            val(errorState) = allValues
            assertEquals(errorState, state)

        }

    }

    private fun createFakeList(): List<TvShow> {
        return mutableListOf(TvShow(1, "name1", "image1", "score1"), TvShow(2, "name2", "image2", "score2"))
    }
}