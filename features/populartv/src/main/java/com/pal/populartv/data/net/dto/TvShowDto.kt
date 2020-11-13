package com.pal.populartv.data.net.dto

import com.google.gson.annotations.SerializedName
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.data.net.ApiConstants

data class
TvShowDto(
    @SerializedName("name") val name: String,
    @SerializedName("backdrop_path") val image: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val score: String
) {
    fun toDomain(): TvShow = TvShow(id, name, ApiConstants.BASE_IMAGE_URL + image, score)
}
