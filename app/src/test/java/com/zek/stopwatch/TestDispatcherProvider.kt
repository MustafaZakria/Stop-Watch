package com.zek.stopwatch

import com.zek.stopwatch.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher


class TestDispatcherProvider(
    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()
): DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val unconfined: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}