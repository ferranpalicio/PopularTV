package com.pal.populartv.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pal.populartv.data.local.TvShowRoomEntity.Companion.TVSHOW_TABLE_NAME
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.domain.entity.TvShow

@Entity(tableName = TVSHOW_TABLE_NAME)
data class TvShowRoomEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = TVSHOW_NAME) val name: String,
    @ColumnInfo(name = TVSHOW_IMAGE) val image: String,
    @ColumnInfo(name = TVSHOW_SCORE) val score: String,
    @ColumnInfo(name = TVSHOW_PAGE) var page: Int
) {
    fun toDomain(): TvShow = TvShow(id, name, ApiConstants.BASE_IMAGE_URL + image, score)

    companion object {
        const val TVSHOW_TABLE_NAME = "tvShows"

        const val TVSHOW_NAME = "tv_show_name"
        const val TVSHOW_IMAGE = "tv_show_image"
        const val TVSHOW_SCORE = "tv_show_score"
        const val TVSHOW_PAGE = "tv_show_page"
    }
}