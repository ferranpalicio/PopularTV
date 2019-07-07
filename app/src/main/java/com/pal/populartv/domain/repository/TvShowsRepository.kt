package com.pal.populartv.domain.repository

import com.pal.populartv.domain.entity.TvShow


interface  TvShowsRepository {
   suspend fun getTvShows():Result<List<TvShow>>
}