package com.pal.populartv.data

import com.pal.core.common.AsyncResult
import com.pal.populartv.data.settings.PopularTvSettings
import com.pal.populartv.data.local.LocalDataProvider
import com.playground.database.entities.TvShowRoomEntity
import com.pal.populartv.data.mapper.NetworkToLocalMapper
import com.pal.populartv.data.net.NetworkDataProvider
import com.pal.populartv.data.mapper.DatabaseToEntityMapper
import com.pal.populartv.data.net.ApiConstants.Companion.INITAL_PAGE
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
    private val popularTvSettings: PopularTvSettings
) : TvShowsRepository {

    //    private var page = 0
    private val timeRange = 10000 * 6 //1 min todo extract functionality

    override suspend fun getTvShows(page: Int): AsyncResult<Pair<List<TvShow>, Int>> {

        if (System.currentTimeMillis() - popularTvSettings.lastTimeDataSaved() > timeRange) {// todo extract functionality

            //invalidate local data
            localDataProvider.removeData()

            //todo reset content from screen
            //page++
            return getDataFromNetworkAndSaveIt(INITAL_PAGE)

        } else {
            //return if (page == 0) {
            val localData: List<TvShowRoomEntity> =
                localDataProvider.requestData(page)
            //page = localData.last().page
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
            /*} else {
                //page++
                return try {
                    val roomData: List<TvShowRoomEntity> =
                        getDataFromNetworkAndSaveIt(page)
                    AsyncResult.Success(roomData.map { databaseToEntityMapper.mapFromRemote(it) })
                } catch (e: Exception) {
                    AsyncResult.Error(e.localizedMessage)
                }

            }*/
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