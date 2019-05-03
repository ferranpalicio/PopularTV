package com.pal.populartv.net.dto

import com.google.gson.annotations.SerializedName
import com.pal.populartv.entity.TvShow
import com.pal.populartv.net.ApiConstants

data class TvShowDto(
    @SerializedName("name") val name: String,
    @SerializedName("backdrop_path") val image: String,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val score: String
) {
    fun TvShowDto.toValueObject(): TvShow = TvShow(id, name, ApiConstants.BASE_IMAGE_URL + image, score)
}