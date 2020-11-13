package com.playground.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.playground.database.dao.TvShowDao
import com.playground.database.entities.TvShowRoomEntity

@Database(entities = [TvShowRoomEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
}