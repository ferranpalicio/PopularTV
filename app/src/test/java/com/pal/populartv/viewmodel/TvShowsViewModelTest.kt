package com.pal.populartv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.pal.populartv.data.TvShowsRepositoryImpl
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.testObserver
import com.pal.populartv.ui.ScreenState
import com.pal.populartv.utils.TestContextProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: TvShowsViewModel

    private val mockRepository: TvShowsRepositoryImpl = mock()
        /*useConstructor = UseConstructor.withArguments(
            mockLocalDataProvider,
            mockNetworkDataProvider,
            mockNetworkToLocalMapper,
            mockAppSettings
        )
    )*/

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TvShowsViewModel(mockRepository, TestContextProvider())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()// reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun update_live_data_for_successful_state()  = testDispatcher.runBlockingTest {

        val resultOk = Result.success(createFakeList())
        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()

        whenever(mockRepository.getTvShows()).thenReturn(resultOk)

        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is ScreenState.Loading)
        assert(liveDataUnderTest.observedValues[1] is ScreenState.Success)

    }

    @Test
    fun update_live_data_for_error_state() = testDispatcher.runBlockingTest {

        whenever(mockRepository.getTvShows()).thenReturn(Result.failure(IOException("error message")))

        val liveDataUnderTest = viewModel.tvShowsLiveData.testObserver()
        viewModel.getTvShows()

        assert(liveDataUnderTest.observedValues.size == 2)
        assert(liveDataUnderTest.observedValues[0] is ScreenState.Loading)
        assert(liveDataUnderTest.observedValues[1] is ScreenState.Error)

    }

    private fun createFakeList() =
        listOf(TvShow(1, "name1", "image1", "score1"), TvShow(2, "name2", "image2", "score2"))
}