package com.pal.core.data_contract

//todo uncouple data types from data managment
interface DataPersister<T>: DataProvider<T, Nothing> {
    suspend fun persistData(data: T)
    suspend fun removeData()
}