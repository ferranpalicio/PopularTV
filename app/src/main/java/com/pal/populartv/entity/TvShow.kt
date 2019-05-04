package com.pal.populartv.entity


data class TvShow(
    val id: Int,
    val name: String,
    val image: String,
    val score: String
) {
    sealed class State {
        object Loading : State()
        class Succes(val tvShows: List<TvShow>) : State()
        class Error(val message: String) : State()
    }
}