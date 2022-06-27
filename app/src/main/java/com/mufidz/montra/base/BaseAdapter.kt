package com.mufidz.montra.base

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Entity : Parcelable, Listener: ItemClick> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemListener: Listener? = null

    var list: MutableList<Entity> = mutableListOf()

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: MutableList<Entity>) {
        list = item
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        list = mutableListOf()
        notifyDataSetChanged()
    }
}