package com.pal.core.data_contract

interface DataProvider<T, D> {
    suspend fun requestData(page: Int): T
    suspend fun requestDetailData(id: Int): D? = null//todo
}