package com.pal.populartv.data

import com.pal.core.common.AsyncResult
import com.pal.populartv.data.local.FeedStoragePolicy
import com.pal.populartv.data.settings.PopularTvSettings
import com.pal.populartv.data.local.LocalDataProvider
import com.playground.database.entities.TvShowRoomEntity
import com.pal.populartv.data.mapper.NetworkToLocalMapper
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.data.mapper.DatabaseToEntityMapper
import com.pal.populartv.data.net.ApiConstants.Companion.INITIAL_PAGE
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
    private val popularTvSettings: PopularTvSettings,
    private val feedStoragePolicy: FeedStoragePolicy
) : TvShowsRepository {

    override suspend fun getTvShows(page: Int): AsyncResult<Pair<List<TvShow>, Int>> {

        if (feedStoragePolicy.isInvalidData()) {
            localDataProvider.removeData()
            return getDataFromNetworkAndSaveIt(INITIAL_PAGE)

        } else {
            val localData: List<TvShowRoomEntity> =
                localDataProvider.requestData(page)
            return if (localData.isEmpty()) {
                getDataFromNetworkAndSaveIt(page)
            } else {
                AsyncResult.Success(
                    Pair(
                        localData.map { databaseToEntityMapper.mapFromRemote(it) },
                        page
                    )
                )
            }
        }
    }

    private suspend fun getDataFromNetworkAndSaveIt(page: Int): AsyncResult<Pair<List<TvShow>, Int>> {
        val finalPage = if (page <= 0) 1 else page

        return try {
            val networkData: List<TvShowDto> =
                networkDataProvider.requestData(finalPage)
            val roomData: List<TvShowRoomEntity> =
                networkData.map {
                    networkToLocalMapper.mapFromRemote(Pair(it, finalPage))
                }

            localDataProvider.persistData(roomData)

            popularTvSettings.updateLastTimeSaved(System.currentTimeMillis())
            AsyncResult.Success(
                Pair(
                    roomData.map { databaseToEntityMapper.mapFromRemote(it) },
                    finalPage
                )
            )
        } catch (e: Exception) {
            AsyncResult.Error(e.localizedMessage)
        }
    }
}