package com.pal.populartv.data.mapper

import com.playground.database.entities.TvShowRoomEntity
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.domain.entity.TvShow
import javax.inject.Inject

class DatabaseToEntityMapper @Inject constructor() : EntityMapper<TvShowRoomEntity, TvShow> {
    override fun mapFromRemote(from: TvShowRoomEntity): TvShow {
        //fun toDomain(): TvShow = TvShow(id, name, ApiConstants.BASE_IMAGE_URL + image, score)
        return TvShow(from.id, from.name, ApiConstants.BASE_IMAGE_URL + from.image, from.score)
    }

}