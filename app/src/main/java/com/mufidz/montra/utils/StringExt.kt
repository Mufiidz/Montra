package com.mufidz.montra.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.toRp(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.maximumFractionDigits = 0
    return format.format(this)
}

fun Any.convertToDate(pattern: String?): String {
    SimpleDateFormat(
        pattern ?: "EEEE, dd MMMM yyyy HH:mm:ss z",
        Locale("id", "ID")
    ).also {
        return when (this) {
            is Long -> it.format(this)
            else -> "-"
        }
    }
}