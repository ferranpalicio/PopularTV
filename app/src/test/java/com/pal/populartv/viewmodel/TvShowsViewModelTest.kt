package com.pal.populartv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.pal.populartv.CoroutinesTestRule
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.testObserver
import com.pal.populartv.ui.ScreenState
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    lateinit var viewModel: TvShowsViewModel
    private val networkDataProvider: NetworkDataProvider = mock()

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(networkDataProvider)
    }

    @Test
    fun update_live_data_for_successful_state() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val resultOk = Result.success(createFakeList())
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        whenever(networkDataProvider.requestData(any())).then { invocation ->
            (invocation.arguments[0] as (Result<List<TvShow>>) -> Unit).invoke(resultOk)
        }

        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is ScreenState.Loading)
        assert(liveDataUnderTest.observedValues[1] is ScreenState.Success)

    }

    @Test
    fun update_live_data_for_error_state() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val resultError = Result.failure<Exception>(Exception())
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        whenever(networkDataProvider.requestData(any())).then { invocation ->
            (invocation.arguments[0] as (Result<Exception>) -> Unit).invoke(resultError)
        }

        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is ScreenState.Loading)
        assert(liveDataUnderTest.observedValues[1] is ScreenState.Error)

    }

    private fun createFakeList() =
        listOf(TvShow(1, "name1", "image1", "score1"), TvShow(2, "name2", "image2", "score2"))
}