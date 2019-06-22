package com.pal.populartv.ui

import com.pal.populartv.domain.entity.TvShow


sealed class ScreenState {
    object Loading : ScreenState()
    class Success(val tvShows: List<TvShow>) : ScreenState()
    class Error(val message: String) : ScreenState()
}