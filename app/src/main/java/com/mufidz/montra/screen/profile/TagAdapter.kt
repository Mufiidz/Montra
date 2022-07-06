package com.mufidz.montra.screen.profile

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.databinding.ItemDiscountBinding
import com.mufidz.montra.utils.inflate
import java.util.*

class TagAdapter : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    var list: MutableList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateAt(position: Int, tag: String) {
        if (list.isEmpty()) list.add(tag) else list.add(position, tag)
        notifyDataSetChanged()
    }

    fun onMoveItem(from: Int, to: Int) {
        Collections.swap(list, from, to)
        notifyItemMoved(from, to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDiscountBinding.bind(parent.inflate(R.layout.item_discount))
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    inner class ViewHolder(private val binding: ItemDiscountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: String, position: Int) {
            with(binding) {
                txtTitle.text = "Tag ${position + 1}"
                txtDiscount.text = data
            }
        }
    }
}