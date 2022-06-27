package com.mufidz.montra.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun dispatcherIO(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}

class DispatcherProviderImpl : DispatcherProvider {
    override fun dispatcherIO() = Dispatchers.IO
    override fun default() = Dispatchers.Default
}