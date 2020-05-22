package com.pal.playgorund.data.mapper

import com.pal.playgorund.data.local.TvShowRoomEntity
import com.pal.playgorund.data.net.dto.TvShowDto
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NetworkToLocalMapper @Inject constructor() : EntityMapper<Pair<TvShowDto, Int>, TvShowRoomEntity> {
    override fun mapFromRemote(from: Pair<TvShowDto, Int>): TvShowRoomEntity =
        TvShowRoomEntity(
            from.first.id,
            from.first.name,
            from.first.image ?: "",
            from.first.score,
            from.second
        )
}