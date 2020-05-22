package com.pal.playgorund

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseCoroutineTester {

    protected val testDispatcher = TestCoroutineDispatcher()

    @Before
    abstract fun setUp()

    @After
    open fun tearDown() {
        Dispatchers.resetMain()// reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }
}