package com.mufidz.montra.screen.home

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseAdapter
import com.mufidz.montra.databinding.ItemReportBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.utils.convertToDate
import com.mufidz.montra.utils.inflate
import com.mufidz.montra.utils.toRp

class ReportAdapter : BaseAdapter<Report, HomeListener>() {

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        onItemListener?.getItemCount(list.size)
    }

    fun updateAt(position: Int, report: Report) {
        if (list.isEmpty()) list.add(report) else list.add(position, report)
        notifyDataSetChanged()
        onItemListener?.getItemCount(list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(ItemReportBinding.bind(parent.inflate(R.layout.item_report)))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(list[position])
    }

    inner class ViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Report) {
            with(binding) {
                textTitleItemReport.text = data.title
                textAmountItemReport.apply {
                    text = data.amount.toRp()
                    setTextColor(ContextCompat.getColor(context, if (data.isIncome) R.color.purple_500 else R.color.error))
                }
                textTagItemReport.text = data.tag
                textDateItemReport.text = data.createdTime.convertToDate("EEEE, dd MMMM yyyy HH:mm")
                onItemListener?.getItem(data)
                root.setOnClickListener {
                    onItemListener?.onItemClick(data)
                }
            }
        }
    }
}
