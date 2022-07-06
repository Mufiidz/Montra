package com.mufidz.montra.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dashboard(
    val amount: Int = 0,
    val income: Int = 0,
    val outcome: Int = 0
) : Parcelable
