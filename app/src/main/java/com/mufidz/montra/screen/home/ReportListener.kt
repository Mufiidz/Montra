package com.mufidz.montra.screen.home

import com.mufidz.montra.base.ItemClick
import com.mufidz.montra.entity.Report

interface ReportListener : ItemClick {
    fun onItemClick(data: Report)
    fun getItemCount(count: Int)
}