package com.mufidz.montra.base

interface ItemListener<T> : ItemClick {
    fun onItemClick(data: T)
}