package com.pal.playgorund.utils

import com.pal.core.di.common.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined


class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineDispatcher = Unconfined
    override val io: CoroutineDispatcher = Unconfined
}
