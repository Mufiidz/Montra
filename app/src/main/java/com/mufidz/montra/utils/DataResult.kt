package com.mufidz.montra.utils

import com.mufidz.montra.base.UseCaseResult

sealed class DataResult<out T>  : UseCaseResult(){
    data class Success<T>(val data: T) : DataResult<T>()
    data class Failed(val message: String) : DataResult<Nothing>()
}