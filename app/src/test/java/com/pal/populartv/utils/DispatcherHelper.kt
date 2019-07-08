package com.pal.populartv.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlin.coroutines.CoroutineContext


class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineDispatcher = Unconfined
    override val io: CoroutineDispatcher = Unconfined
}
