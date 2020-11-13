package com.pal.populartv.data

interface DataProvider<T, D> {
    suspend fun requestPagedData(page: Int): T
    suspend fun requestDetailData(id: Int): D? = null
}