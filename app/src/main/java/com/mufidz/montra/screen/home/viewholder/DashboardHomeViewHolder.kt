package com.mufidz.montra.screen.home.viewholder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemDashboardHomeBinding
import com.mufidz.montra.screen.home.DashboardHome
import com.mufidz.montra.screen.home.ReportListener
import com.mufidz.montra.utils.toRp

class DashboardHomeViewHolder(
    private val binding: ItemDashboardHomeBinding,
    private val listener: ReportListener?
) :
    BaseViewHolder<DashboardHome>(binding.root) {

    override fun bind(item: DashboardHome) {
        val amount = item.dashboard?.amount ?: 0
        val income = item.dashboard?.income ?: 0
        val outcome = item.dashboard?.outcome ?: 0
        val isVisibleBalance = item.isVisibleBalance
        binding.apply {
            card.apply { visibility = View.VISIBLE }
            txtAmount.apply {
                text = isVisibleBalance.changeVisible(amount.toRp())
                textAmountColor(amount, isVisibleBalance)
            }
            txtIncome.text = isVisibleBalance.changeVisible(income.toRp())
            txtOutcome.text = isVisibleBalance.changeVisible(outcome.toRp())
            btnVisibility.apply {
                isChecked = isVisibleBalance
                setOnCheckedChangeListener { _, isChecked ->
                    binding.txtAmount.textAmountColor(amount, isChecked)
                    listener?.onBalanceVisibilityChange(isChecked)
                    txtAmount.text = isChecked.changeVisible(amount.toRp())
                    txtIncome.text = isChecked.changeVisible(income.toRp())
                    txtOutcome.text = isChecked.changeVisible(outcome.toRp())
                }
            }
        }
    }

    private fun TextView.textAmountColor(amount: Int, isVisibleBalance: Boolean) {
        val amountColor = if (isVisibleBalance) R.color.text else {
            if (amount < 0) R.color.error else if (amount == 0) R.color.text else R.color.primary
        }
        setTextColor(
            ContextCompat.getColor(
                context,
                amountColor
            )
        )
    }

    private fun Boolean.changeVisible(value: String) = if (!this) value else "Rp---"
}
