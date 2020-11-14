package com.pal.core.common

sealed class AsyncResult<out T> {
    object Loading : AsyncResult<Nothing>()
    class Success<out T>(val data: T) : AsyncResult<T>()
    class Error(val message: String) : AsyncResult<Nothing>()
}