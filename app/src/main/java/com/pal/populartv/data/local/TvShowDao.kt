package com.pal.populartv.data.local

import android.database.sqlite.SQLiteDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pal.populartv.data.local.TvShowRoomEntity.Companion.TVSHOW_PAGE
import com.pal.populartv.data.local.TvShowRoomEntity.Companion.TVSHOW_TABLE_NAME

@Dao
interface TvShowDao {
    @Query("SELECT * FROM $TVSHOW_TABLE_NAME ORDER BY $TVSHOW_PAGE ASC")
    suspend fun getAllTvShows(): List<TvShowRoomEntity>

    @Query("SELECT * FROM $TVSHOW_TABLE_NAME WHERE $TVSHOW_PAGE LIKE :page")
    suspend fun getTvShowsForPage(page: Int): List<TvShowRoomEntity>

    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    suspend fun insertAll(tvShows: List<TvShowRoomEntity>)

    @Query("DELETE FROM $TVSHOW_TABLE_NAME")
    suspend fun nukeTable()
}