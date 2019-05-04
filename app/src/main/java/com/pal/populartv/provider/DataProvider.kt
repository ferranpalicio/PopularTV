package com.pal.populartv.provider

interface DataProvider<T> {
    fun requestData(callback: (item: T) -> Unit)
    //fun requestData(): T = throw NotImplementedError()
}