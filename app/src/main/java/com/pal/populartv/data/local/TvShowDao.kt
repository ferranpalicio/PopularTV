package com.pal.populartv.data.local

import android.database.sqlite.SQLiteDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pal.populartv.data.local.TvShowRoomEntity.Companion.TVSHOW_TABLE_NAME

@Dao
interface TvShowDao {
    @Query("SELECT * FROM $TVSHOW_TABLE_NAME")
    suspend fun getTvShows(): List<TvShowRoomEntity>

    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    suspend fun insertAll(vararg tvShows: TvShowRoomEntity)
}