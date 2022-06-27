package com.mufidz.montra.utils

sealed class ResultData<out T> {
    data class Success<T>(val value: T) : ResultData<T>()
    data class Error(val exception: Exception) : ResultData<Nothing>()
    data class Canceled(val exception: Exception?) : ResultData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $value]"
            is Error -> "Error[exception = $exception]"
            is Canceled -> "Canceled[exception = $exception"
        }
    }
}
