package com.pal.playgorund.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.pal.core.common.AsyncResult
import com.pal.playgorund.BaseCoroutineTester
import com.pal.playgorund.testObserver
import com.pal.populartv.data.net.ApiConstants.Companion.INITIAL_PAGE
import com.pal.populartv.domain.entity.TvShow
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowsViewModelTest : BaseCoroutineTester() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: com.pal.populartv.viewmodel.TvShowsViewModel

    private val mockRepository: com.pal.populartv.data.TvShowsRepositoryImpl = mock()

    @Before
    override fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = com.pal.populartv.viewmodel.TvShowsViewModel(
            mockRepository
        )
    }

    @Test
    fun `update live data for successful state`() = testDispatcher.runBlockingTest {

        val resultOk = AsyncResult.Success(Pair(createFakeList(), INITIAL_PAGE))
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        whenever(mockRepository.getTvShows(INITIAL_PAGE)).thenReturn(resultOk)

        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is AsyncResult.Loading)
        assert(liveDataUnderTest.observedValues[1] is AsyncResult.Success)

    }

    @Test
    fun `update live data for error state`() = testDispatcher.runBlockingTest {

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