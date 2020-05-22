package com.pal.playgorund.data

import com.pal.playgorund.data.local.LocalDataProvider
import com.pal.playgorund.data.local.TvShowRoomEntity
import com.pal.playgorund.data.mapper.NetworkToLocalMapper
import com.pal.playgorund.data.net.NetworkDataProvider
import com.pal.playgorund.data.net.dto.TvShowDto
import com.pal.playgorund.domain.AppSettings
import com.pal.playgorund.domain.entity.TvShow
import com.pal.playgorund.domain.repository.TvShowsRepository
import dagger.Reusable
import java.lang.Exception
import javax.inject.Inject

@Reusable
class TvShowsRepositoryImpl @Inject constructor(
    private val localDataProvider: LocalDataProvider,
    private val networkDataProvider: NetworkDataProvider,
    private val networkToLocalMapper: NetworkToLocalMapper,
    private val appSettings: AppSettings
) : TvShowsRepository {

    private var page = 0
    private val timeRange = 10000 * 6 //1 min todo extract functionality

    override suspend fun getTvShows(): Result<List<TvShow>> {

        if (System.currentTimeMillis() - appSettings.lastTimeDataSaved() > timeRange) {

            localDataProvider.removeData()

            page++
            return try {
                val roomData: List<TvShowRoomEntity> = getDataFromNetworkAndSaveIt(page)
                Result.success(roomData.map { it.toDomain() })
            } catch (e: Exception) {
                Result.failure(e)
            }

        } else {
            return if (page == 0) {
                val localData: List<TvShowRoomEntity> = localDataProvider.requestData(page)
                page = localData.last().page
                Result.success(localData.map { it.toDomain() })
            } else {
                page++
                return try {
                    val roomData: List<TvShowRoomEntity> = getDataFromNetworkAndSaveIt(page)
                    Result.success(roomData.map { it.toDomain() })
                }catch (e: Exception) {
                    Result.failure(e)
                }

            }
        }
    }

    private suspend fun getDataFromNetworkAndSaveIt(newPage: Int): List<TvShowRoomEntity> {
        val networkData: List<TvShowDto> = networkDataProvider.requestData(newPage)
        val roomData: List<TvShowRoomEntity> =
            networkData.map {
                networkToLocalMapper.mapFromRemote(Pair(it, newPage))
            }

        localDataProvider.persistData(roomData)

        appSettings.updatLastTimeSaved(System.currentTimeMillis())

        return roomData

    }
}