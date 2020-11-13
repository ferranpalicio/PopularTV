package com.pal.populartv.data

import com.pal.core.di.common.AsyncResult
import com.pal.core.domain.AppSettings
import com.pal.populartv.data.local.LocalDataProvider
import com.playground.database.entities.TvShowRoomEntity
import com.pal.populartv.data.mapper.NetworkToLocalMapper
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.data.mapper.DatabaseToEntityMapper
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import dagger.Reusable
import java.lang.Exception
import javax.inject.Inject

@Reusable
class TvShowsRepositoryImpl @Inject constructor(
    private val localDataProvider: LocalDataProvider,
    private val networkDataProvider: NetworkDataProvider,
    private val networkToLocalMapper: NetworkToLocalMapper,
    private val databaseToEntityMapper: DatabaseToEntityMapper,
    private val appSettings: AppSettings
) : TvShowsRepository {

    private var page = 0
    private val timeRange = 10000 * 6 //1 min todo extract functionality

    override suspend fun getTvShows(): AsyncResult<List<TvShow>> {

        if (System.currentTimeMillis() - appSettings.lastTimeDataSaved() > timeRange) {

            localDataProvider.removeData()

            page++
            return try {
                val roomData: List<TvShowRoomEntity> =
                    getDataFromNetworkAndSaveIt(page)
                AsyncResult.Success(roomData.map { databaseToEntityMapper.mapFromRemote(it) })
            } catch (e: Exception) {
                AsyncResult.Error(e.localizedMessage)
            }

        } else {
            return if (page == 0) {
                val localData: List<TvShowRoomEntity> =
                    localDataProvider.requestPagedData(page)
                page = localData.last().page
                AsyncResult.Success(localData.map { databaseToEntityMapper.mapFromRemote(it) })
            } else {
                page++
                return try {
                    val roomData: List<TvShowRoomEntity> =
                        getDataFromNetworkAndSaveIt(page)
                    AsyncResult.Success(roomData.map { databaseToEntityMapper.mapFromRemote(it) })
                } catch (e: Exception) {
                    AsyncResult.Error(e.localizedMessage)
                }

            }
        }
    }

    private suspend fun getDataFromNetworkAndSaveIt(newPage: Int): List<TvShowRoomEntity> {
        val networkData: List<TvShowDto> =
            networkDataProvider.requestPagedData(newPage)
        val roomData: List<TvShowRoomEntity> =
            networkData.map {
                networkToLocalMapper.mapFromRemote(Pair(it, newPage))
            }

        localDataProvider.persistData(roomData)

        appSettings.updateLastTimeSaved(System.currentTimeMillis())

        return roomData

    }
}