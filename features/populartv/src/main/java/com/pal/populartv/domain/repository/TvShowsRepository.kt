package com.pal.populartv.domain.repository

import com.pal.core.di.common.AsyncResult
import com.pal.populartv.domain.entity.TvShow

interface TvShowsRepository {
    suspend fun getTvShows(): AsyncResult<List<TvShow>>
}