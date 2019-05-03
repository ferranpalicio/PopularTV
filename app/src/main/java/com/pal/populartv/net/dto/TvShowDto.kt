package com.pal.populartv.net.dto

import com.google.gson.annotations.SerializedName


data class TvShowDto (
    @SerializedName("name") val name: String,
    @SerializedName("backdrop_path") val image: String,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val score: String
)