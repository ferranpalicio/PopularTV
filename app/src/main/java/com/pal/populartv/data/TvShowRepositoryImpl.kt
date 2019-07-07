package com.pal.populartv.data

import com.pal.populartv.data.local.LocalDataProvider
import com.pal.populartv.data.local.TvShowRoomEntity
import com.pal.populartv.data.mapper.NetworkToLocalMapper
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.data.net.dto.TvShowDto
import com.pal.populartv.domain.AppSettings
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.domain.repository.TvShowsRepository
import dagger.Reusable
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@Reusable
class TvShowRepositoryImpl @Inject constructor(
    private val localDataProvider: LocalDataProvider,
    private val networkDataProvider: NetworkDataProvider,
    private val networkToLocalMapper: NetworkToLocalMapper,
    private val appSettings: AppSettings
) : TvShowsRepository {

    private var page = 0
    private val timeRange = 10000 * 6 //1 min todo extract functionality

    override suspend fun getTvShows(): Result<List<TvShow>> {

        if (System.currentTimeMillis() - appSettings.lastTimeDataSaved() > timeRange) {
            return try {
                localDataProvider.removeData()

                page++
                val networkData: List<TvShowDto> = getDataFromNetworkAndSaveIt(page)

                Result.success(networkData.map { it.toDomain() })
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
                val savedData = localDataProvider.requestData(page)
                if (savedData.isEmpty()) {
                    val networkData: List<TvShowDto> = getDataFromNetworkAndSaveIt(page)
                    Result.success(networkData.map { it.toDomain() })
                } else {
                    Result.success(savedData.map { it.toDomain() })
                }
            }
        }
    }

    private suspend fun getDataFromNetworkAndSaveIt(newPage: Int): List<TvShowDto> {
        val networkData: List<TvShowDto> = networkDataProvider.requestData(newPage)
        val roomData: List<TvShowRoomEntity> =
            networkData.map {
                networkToLocalMapper.mapFromRemote(Pair(it, newPage)) }

        localDataProvider.persistData(roomData)

        appSettings.updatLastTimeSaved(System.currentTimeMillis())

        return networkData

    }
}