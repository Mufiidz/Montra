package com.mufidz.montra.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyPlan(
    var id: Int = 0,
    val title: String = "",
    val amount: Int = 0,
    val quantity: Int = 1,
    val note: String? = null
) : Parcelable
