package com.pal.populartv.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pal.populartv.data.local.TvShowRoomEntity.Companion.TVSHOW_TABLE_NAME

@Entity(tableName = TVSHOW_TABLE_NAME)
data class TvShowRoomEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = TVSHOW_NAME) val name: String,
    @ColumnInfo(name = TVSHOW_IMAGE) val image: String,
    @ColumnInfo(name = TVSHOW_SCORE) val score: String
) {
    companion object {
        const val TVSHOW_TABLE_NAME = "tvShows"

        const val TVSHOW_NAME = "tv_show_name"
        const val TVSHOW_IMAGE = "tv_show_image"
        const val TVSHOW_SCORE = "tv_show_score"
    }
}