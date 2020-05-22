package com.pal.playgorund.ui

import com.pal.playgorund.domain.entity.TvShow


sealed class ScreenState {
    object Loading : ScreenState()
    class Success(val tvShows: List<TvShow>) : ScreenState()
    class Error(val message: String) : ScreenState()
}