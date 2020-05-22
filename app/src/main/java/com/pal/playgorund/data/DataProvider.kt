package com.pal.playgorund.data

interface DataProvider<T> {
    //suspend fun requestData(callback: (item: T) -> Unit)
    suspend fun requestData(page: Int): T
}