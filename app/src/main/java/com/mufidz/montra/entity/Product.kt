package com.mufidz.montra.entity

import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(
    val title: String,
    val destination: Int? = null,
    val bundle: Bundle? = null
) : Parcelable
