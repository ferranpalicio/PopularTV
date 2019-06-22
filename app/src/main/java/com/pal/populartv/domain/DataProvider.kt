package com.pal.populartv.domain

interface DataProvider<T> {
    suspend fun requestData(callback: (item: T) -> Unit)
}