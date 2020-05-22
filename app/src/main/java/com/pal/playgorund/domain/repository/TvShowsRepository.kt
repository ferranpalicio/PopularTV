package com.pal.playgorund.domain.repository

import com.pal.playgorund.domain.entity.TvShow


interface  TvShowsRepository {
   suspend fun getTvShows():Result<List<TvShow>>
}