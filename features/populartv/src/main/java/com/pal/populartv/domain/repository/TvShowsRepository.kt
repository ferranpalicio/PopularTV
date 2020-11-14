package com.pal.populartv.domain.repository

import com.pal.core.common.AsyncResult
import com.pal.populartv.domain.entity.TvShow

interface TvShowsRepository {
    suspend fun getTvShows(page: Int): AsyncResult<Pair<List<TvShow>, Int>>
}