package com.pal.populartv.net.dto

import com.google.gson.annotations.SerializedName


data class WrapperResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val data: List<T>
)