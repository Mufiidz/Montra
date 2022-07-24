package com.mufidz.montra.screen.plan

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseAdapter
import com.mufidz.montra.base.ItemListener
import com.mufidz.montra.databinding.ItemDiscountBinding
import com.mufidz.montra.entity.MoneyPlan
import com.mufidz.montra.utils.inflate
import com.mufidz.montra.utils.toRp

class MoneyPlanAdapter : BaseAdapter<MoneyPlan, ItemListener<MoneyPlan>>() {

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateAt(position: Int, moneyPlan: MoneyPlan) {
        if (list.isEmpty()) list.add(moneyPlan) else list.add(position, moneyPlan)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDiscountBinding.bind(parent.inflate(R.layout.item_discount))
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(list[position], position)
    }

    inner class ViewHolder(private val binding: ItemDiscountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MoneyPlan, position: Int) {
            data.id = position
            with(binding) {
                txtTitle.text = data.title
                txtDiscount.text = data.amount.toRp()
                binding.root.setOnClickListener {
                    onItemListener?.onItemClick(data)
                }
            }
        }
    }
}
