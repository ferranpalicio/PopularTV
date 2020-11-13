package com.pal.populartv.data.net.dto

import com.google.gson.annotations.SerializedName


data class PagedWrapperResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val data: List<T>
)