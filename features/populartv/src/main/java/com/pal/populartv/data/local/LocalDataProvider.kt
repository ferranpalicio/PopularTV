package com.pal.populartv.data.local

import com.playground.database.dao.TvShowDao
import com.playground.database.entities.TvShowRoomEntity
import com.pal.core.data_contract.DataPersister
import dagger.Reusable
import javax.inject.Inject

@Reusable
class LocalDataProvider @Inject constructor(
    private val tvShowDao: TvShowDao
) : DataPersister<List<TvShowRoomEntity>> {
    override suspend fun removeData() {
        tvShowDao.nukeTable()
    }

    override suspend fun persistData(data: List<TvShowRoomEntity>) {
        tvShowDao.insertAll(data)
    }

    override suspend fun requestPagedData(page: Int): List<TvShowRoomEntity> {
        return if (page == 0) {
            tvShowDao.getAllTvShows()
        } else {
            tvShowDao.getTvShowsForPage(page)
        }
    }
}