package com.pal.playgorund.data

import com.nhaarman.mockitokotlin2.*
import com.pal.playgorund.BaseCoroutineTester
import com.pal.playgorund.data.local.LocalDataProvider
import com.pal.playgorund.data.local.TvShowRoomEntity
import com.pal.playgorund.data.mapper.NetworkToLocalMapper
import com.pal.playgorund.data.net.NetworkDataProvider
import com.pal.playgorund.data.net.dto.TvShowDto
import com.pal.playgorund.domain.AppSettings
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
class TvShowsRepositoryImplTest: BaseCoroutineTester() {

    private lateinit var repo: TvShowsRepositoryImpl

    private val mockLocalDataProvider: LocalDataProvider = mock()
    private val mockNetworkDataProvider: NetworkDataProvider = mock()
    private val mockNetworkToLocalMapper: NetworkToLocalMapper = mock()
    private val mockAppSettings: AppSettings = mock()

    @Before
    override fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = TvShowsRepositoryImpl(mockLocalDataProvider, mockNetworkDataProvider, mockNetworkToLocalMapper, mockAppSettings)
    }

    @Test
    fun performs_network_request_if_local_data_has_expired() = testDispatcher.runBlockingTest {

        whenever(mockAppSettings.lastTimeDataSaved()).thenReturn(0)
        val fakeNetworkList = createFakeNetworkList()
        whenever(mockNetworkDataProvider.requestData(anyInt())).thenReturn(fakeNetworkList)
        whenever(mockNetworkToLocalMapper.mapFromRemote(any())).thenReturn(createFakeLocalList()[0])
        repo.getTvShows()

        inOrder(mockLocalDataProvider, mockNetworkDataProvider, mockNetworkToLocalMapper, mockAppSettings).apply {
            verify(mockLocalDataProvider).removeData()
            verify(mockNetworkDataProvider).requestData(anyInt())
            verify(mockNetworkToLocalMapper, times(fakeNetworkList.size)).mapFromRemote(any())
            verify(mockLocalDataProvider).persistData(any())
            verify(mockAppSettings).updatLastTimeSaved(anyLong())
        }
    }

    @Test
    fun get_all_local_if_its_valid() = testDispatcher.runBlockingTest {
        whenever(mockAppSettings.lastTimeDataSaved()).thenReturn(System.currentTimeMillis())
        whenever(mockLocalDataProvider.requestData(anyInt())).thenReturn(createFakeLocalList())
        repo.getTvShows()

        verify(mockLocalDataProvider).requestData(0)
    }

    @Test
    fun ask_for_network_data_if_has_no_local_data_for_new_page() = testDispatcher.runBlockingTest {
        whenever(mockAppSettings.lastTimeDataSaved()).thenReturn(System.currentTimeMillis())
        val fakeLocalList = createFakeLocalList()
        whenever(mockLocalDataProvider.requestData(anyInt())).thenReturn(fakeLocalList)
        whenever(mockNetworkDataProvider.requestData(anyInt())).thenReturn(createFakeNetworkList())
        whenever(mockNetworkToLocalMapper.mapFromRemote(any())).thenReturn(fakeLocalList[0])

        repo.getTvShows()
        repo.getTvShows()

        inOrder(mockLocalDataProvider, mockNetworkDataProvider, mockAppSettings).apply {
            verify(mockLocalDataProvider).requestData(0)
            verify(mockNetworkDataProvider).requestData(2)
            verify(mockLocalDataProvider).persistData(any())
            verify(mockAppSettings).updatLastTimeSaved(anyLong())
        }
    }

    private fun createFakeNetworkList() =
            listOf(TvShowDto("name1", "image1", 1, "score1"), TvShowDto("name2", "image2", 2, "score2"))

    private fun createFakeLocalList() =
            listOf(TvShowRoomEntity(1, "name1", "image1", "score1", 1), TvShowRoomEntity(2, "name2", "image2", "score2", 1))
}