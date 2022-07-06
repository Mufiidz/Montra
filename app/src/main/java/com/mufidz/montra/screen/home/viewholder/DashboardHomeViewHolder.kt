package com.mufidz.montra.screen.home.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemDashboardHomeBinding
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.utils.toRp

class DashboardHomeViewHolder(private val binding: ItemDashboardHomeBinding) :
    BaseViewHolder<Dashboard>(binding.root) {
    override fun bind(item: Dashboard) {
        val amount = item.amount
        binding.apply {
            card.apply { visibility = View.VISIBLE }
            txtAmount.apply {
                text = amount.toRp()
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (amount < 0) R.color.error else if (amount == 0) R.color.black else R.color.purple_500
                    )
                )
            }
            txtIncome.text = item.income.toRp()
            txtOutcome.text = item.outcome.toRp()
        }
    }
}
