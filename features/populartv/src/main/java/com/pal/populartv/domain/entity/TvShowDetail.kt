package com.pal.populartv.domain.entity

import java.util.*

data class TvShowDetail(
    val id: Int,
    val name: String,
    val image: String,
    val score: String,
    val overview: String,
    val premiere: Date
)