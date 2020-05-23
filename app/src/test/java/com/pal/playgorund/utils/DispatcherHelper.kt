package com.pal.playgorund.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined


class TestContextProvider : com.pal.populartv.utils.CoroutineContextProvider() {
    override val main: CoroutineDispatcher = Unconfined
    override val io: CoroutineDispatcher = Unconfined
}
