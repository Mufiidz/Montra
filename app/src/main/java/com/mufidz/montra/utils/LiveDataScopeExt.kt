package com.mufidz.montra.utils

import androidx.lifecycle.LiveDataScope
import com.mufidz.montra.base.UseCaseResult

suspend fun LiveDataScope<UseCaseResult>.callUseCase(block: UseCaseResult) {
    emit(block)
}