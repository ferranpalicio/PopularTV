package com.pal.core.di.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


open class CoroutineContextProvider {
    open val io : CoroutineDispatcher = Dispatchers.IO
    open val main : CoroutineDispatcher = Dispatchers.Main
}