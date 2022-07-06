package com.mufidz.montra.screen.discount

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.databinding.ItemDiscountBinding
import com.mufidz.montra.utils.inflate

class DiscountAdapter : RecyclerView.Adapter<DiscountAdapter.ViewHolder>() {

    var list: MutableList<Int> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateAt(position: Int, discount: Int) {
        if (list.isEmpty()) list.add(discount) else list.add(position, discount)
        notifyDataSetChanged()
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

        fun onBind(data: Int, position: Int) {
            with(binding) {
                txtTitle.text = "Discount ${position + 1}"
                txtDiscount.text = "$data%"
            }
        }
    }
}
