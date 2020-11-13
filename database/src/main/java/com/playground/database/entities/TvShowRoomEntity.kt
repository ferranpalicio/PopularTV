package com.playground.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.playground.database.entities.TvShowRoomEntity.Companion.TVSHOW_TABLE_NAME

@Entity(tableName = TVSHOW_TABLE_NAME)
data class TvShowRoomEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = TVSHOW_NAME) val name: String,
    @ColumnInfo(name = TVSHOW_IMAGE) val image: String,
    @ColumnInfo(name = TVSHOW_SCORE) val score: String,
    @ColumnInfo(name = TVSHOW_PAGE) var page: Int
) {

    companion object {
        const val TVSHOW_TABLE_NAME = "tvShows"

        const val TVSHOW_NAME = "tv_show_name"
        const val TVSHOW_IMAGE = "tv_show_image"
        const val TVSHOW_SCORE = "tv_show_score"
        const val TVSHOW_PAGE = "tv_show_page"
    }
}