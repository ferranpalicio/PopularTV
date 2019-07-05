package com.pal.populartv.data.mapper

import com.pal.populartv.data.local.TvShowRoomEntity
import com.pal.populartv.data.net.ApiConstants
import com.pal.populartv.data.net.dto.TvShowDto

class NetworkToLocalMapper : EntityMapper<TvShowDto, TvShowRoomEntity> {
    override fun mapFromRemote(type: TvShowDto): TvShowRoomEntity =
        TvShowRoomEntity(type.id, type.name, ApiConstants.BASE_IMAGE_URL + type.image, type.score)
}