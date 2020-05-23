package com.pal.populartv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TvShowRoomEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
}