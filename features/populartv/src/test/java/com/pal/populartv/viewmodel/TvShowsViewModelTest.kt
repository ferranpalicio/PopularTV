package com.pal.populartv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.pal.core.common.AsyncResult
import com.pal.populartv.CoroutineTestRule
import com.pal.populartv.data.TvShowsRepositoryImpl
import com.pal.populartv.data.net.ApiConstants.Companion.INITIAL_PAGE
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.testObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: TvShowsViewModel

    private val mockRepository: TvShowsRepositoryImpl = mock()

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(
            mockRepository
        )
    }

    @Test
    fun `update live data for successful state`() = runBlockingTest {

        val resultOk = AsyncResult.Success(Pair(createFakeList(), INITIAL_PAGE))
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        whenever(mockRepository.getTvShows(INITIAL_PAGE)).thenReturn(resultOk)

        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is AsyncResult.Loading)
        assert(liveDataUnderTest.observedValues[1] is AsyncResult.Success)
    }

    @Test
    fun `clears live data with new one one because it's invalidated`()= runBlockingTest {
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        val resultOk = AsyncResult.Success(Pair(createFakeList(), INITIAL_PAGE))
        whenever(mockRepository.getTvShows(INITIAL_PAGE)).thenReturn(resultOk)
        viewModel.getTvShows()

        val invalidatedResultOk = AsyncResult.Success(Pair(createFakeList(), INITIAL_PAGE))
        whenever(mockRepository.getTvShows(INITIAL_PAGE + 1)).thenReturn(invalidatedResultOk)
        viewModel.getTvShows()

        assert((liveDataUnderTest.observedValues.last() as AsyncResult.Success).data.size == 2)
    }

    @Test
    fun `update live data for error state`() = runBlockingTest {

        whenever(mockRepository.getTvShows(INITIAL_PAGE)).thenReturn(AsyncResult.Error("error message"))

        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()
        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is AsyncResult.Loading)
        assert(liveDataUnderTest.observedValues[1] is AsyncResult.Error)

    }

    private fun createFakeList() =
        listOf(TvShow(1, "name1", "image1", "score1"), TvShow(2, "name2", "image2", "score2"))
}