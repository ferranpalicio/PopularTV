package com.pal.playgorund.data


interface DataPersister<T>: DataProvider<T> {
    suspend fun persistData(data: T)
    suspend fun removeData()
}