package com.pal.populartv.data.net.dto

import com.google.gson.annotations.SerializedName

data class TvShowDetailDto(
    @SerializedName("name") val name: String,
    @SerializedName("backdrop_path") val image: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val score: String,
    @SerializedName("vote_count") val scoreCount: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("first_air_date") val premiere: String //yyyy-mm-dd
)