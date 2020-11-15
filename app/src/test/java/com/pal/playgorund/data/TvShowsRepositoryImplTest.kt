package com.pal.playgorund.data

import com.nhaarman.mockitokotlin2.*
import com.pal.playgorund.BaseCoroutineTester
import com.pal.populartv.data.TvShowsRepositoryImpl
import com.pal.populartv.data.local.FeedStoragePolicy
import com.pal.populartv.data.local.LocalDataProvider
import com.pal.populartv.data.mapper.DatabaseToEntityMapper
import com.pal.populartv.data.mapper.NetworkToLocalMapper
import com.pal.populartv.data.net.ApiConstants.Companion.INITIAL_PAGE
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.data.settings.PopularTvSettings
import com.playground.database.entities.TvShowRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class TvShowsRepositoryImplTest : BaseCoroutineTester() {

    private lateinit var repo: TvShowsRepositoryImpl

    private val mockLocalDataProvider: LocalDataProvider = mock()
    private val mockNetworkDataProvider: NetworkDataProvider = mock()
    private val networkToLocalMapper: NetworkToLocalMapper = NetworkToLocalMapper()
    private val databaseToLocalMapper: DatabaseToEntityMapper = DatabaseToEntityMapper()
    private val mockPopularTvSettings: PopularTvSettings = mock()
    private val mockStoragePolicy: FeedStoragePolicy = mock()

    @Before
    override fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = TvShowsRepositoryImpl(
            mockLocalDataProvider, mockNetworkDataProvider, networkToLocalMapper,
            databaseToLocalMapper, mockPopularTvSettings, mockStoragePolicy
        )
    }

    @Test
    fun `performs network request if local data it's invalidated`() =
        testDispatcher.runBlockingTest {

            whenever(mockStoragePolicy.isInvalidData()).thenReturn(true)
            val fakeNetworkList = createFakeNetworkResponse()
            whenever(mockNetworkDataProvider.requestData(anyInt())).thenReturn(fakeNetworkList)
            repo.getTvShows(INITIAL_PAGE)

            inOrder(
                mockLocalDataProvider,
                mockNetworkDataProvider,
                mockPopularTvSettings
            ).apply {
                verify(mockLocalDataProvider).removeData()
                verify(mockNetworkDataProvider).requestData(eq(INITIAL_PAGE))
                verify(mockLocalDataProvider).persistData(any())
                verify(mockPopularTvSettings).updateLastTimeSaved(anyLong())
            }
        }

    @Test
    fun `get local data if local storage is valid and have it`() = testDispatcher.runBlockingTest {
        whenever(mockStoragePolicy.isInvalidData()).thenReturn(false)
        whenever(mockLocalDataProvider.requestData(anyInt())).thenReturn(
            createFakeDatabaseResponse(INITIAL_PAGE)
        )
        repo.getTvShows(INITIAL_PAGE)

        verify(mockLocalDataProvider).requestData(eq(INITIAL_PAGE))
        verify(mockNetworkDataProvider, never()).requestData(eq(INITIAL_PAGE))
    }

    @Test
    fun `get network data if local storage is valid but don't have it for requested page`() =
        testDispatcher.runBlockingTest {
            val page = INITIAL_PAGE + 1
            whenever(mockStoragePolicy.isInvalidData()).thenReturn(false)
            whenever(mockLocalDataProvider.requestData(page)).thenReturn(emptyList())

            repo.getTvShows(page)

            verify(mockLocalDataProvider).requestData(eq(page))
            verify(mockNetworkDataProvider).requestData(eq(page))
        }

    private fun createFakeNetworkResponse() =
        listOf(
            com.pal.populartv.data.net.dto.TvShowDto(
                "name1",
                "image1",
                1,
                "score1"
            ),
            com.pal.populartv.data.net.dto.TvShowDto(
                "name2",
                "image2",
                2,
                "score2"
            )
        )

    private fun createFakeDatabaseResponse(page: Int) =
        listOf(
            TvShowRoomEntity(
                1,
                "name1",
                "image1",
                "score1",
                page
            ),
            TvShowRoomEntity(
                2,
                "name2",
                "image2",
                "score2",
                page
            )
        )
}