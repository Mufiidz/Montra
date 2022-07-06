package com.mufidz.montra.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseSection<S: Section> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract val section: MutableList<S>

    override fun getItemCount() = section.size

    override fun getItemViewType(position: Int) = section[position].viewType

    protected fun getSectionPosition(type: Int): Int? =
        section.indexOf(section.find { it.viewType == type }).takeIf { it >= 0 }
}