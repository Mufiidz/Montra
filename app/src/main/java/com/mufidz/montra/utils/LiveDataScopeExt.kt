package com.mufidz.montra.utils

import androidx.lifecycle.LiveDataScope
import com.mufidz.montra.base.ActionResult

suspend fun LiveDataScope<ActionResult>.callUseCase(block: ActionResult) {
    emit(block)
}