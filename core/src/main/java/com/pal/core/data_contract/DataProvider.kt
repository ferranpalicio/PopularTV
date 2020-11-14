package com.pal.core.data_contract

interface DataProvider<T, D> {
    suspend fun requestPagedData(page: Int): T
    suspend fun requestDetailData(id: Int): D? = null
}