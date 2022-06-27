package com.mufidz.montra.screen.home

import com.mufidz.montra.base.ItemClick
import com.mufidz.montra.entity.Report

interface HomeListener : ItemClick {
    fun onItemClick(data: Report)
    fun getItem(report: Report)
    fun getItemCount(count: Int)
}