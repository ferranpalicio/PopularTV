package com.pal.core.data_contract

interface DataPersister<T>: DataProvider<T, Nothing> {
    suspend fun persistData(data: T)
    suspend fun removeData()
}