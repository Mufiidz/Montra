package com.mufidz.montra.screen.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemReportBinding
import com.mufidz.montra.databinding.ItemReportSecretBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.utils.convertToDate
import com.mufidz.montra.utils.inflate
import com.mufidz.montra.utils.toRp

class ReportAdapter(private val isFullHistory: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemListener: ReportListener? = null

    var list: MutableList<Report> = mutableListOf()

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        onItemListener?.getItemCount(list.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAt(position: Int, report: Report) {
        if (list.isEmpty()) list.add(report) else list.add(position, report)
        notifyDataSetChanged()
        onItemListener?.getItemCount(list.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: MutableList<Report>) {
        list = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 1) ReportViewHolderSecret(ItemReportSecretBinding.bind(parent.inflate(R.layout.item_report_secret)))
        else ViewHolder(ItemReportBinding.bind(parent.inflate(R.layout.item_report)))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            list[position].also {
                when (this) {
                    is ReportViewHolderSecret -> bind(it)
                    is ViewHolder -> bind(it)
                }
            }
        }
    }

    override fun getItemCount(): Int = if (!isFullHistory && list.size >= 4) 4 else list.size

    override fun getItemViewType(position: Int): Int = list[position].isSecretToInt()

    inner class ReportViewHolderSecret(private val binding: ItemReportSecretBinding) :
        BaseViewHolder<Report>(binding.root) {
        override fun bind(item: Report) {
            binding.root.setOnClickListener {
                onItemListener?.onItemClick(item)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemReportBinding) :
        BaseViewHolder<Report>(binding.root) {
        override fun bind(item: Report) {
            val updatedTime = item.updatedTime
            with(binding) {
                root.setOnClickListener {
                    onItemListener?.onItemClick(item)
                }
                textTitleItemReport.text = item.title
                textAmountItemReport.apply {
                    text = if (item.isIncome) item.amount.toRp() else "-${item.amount.toRp()}"
                    setTextColor(
                        ContextCompat.getColor(
                            context, if (item.isIncome) R.color.primary else R.color.error
                        )
                    )
                }
                textTagItemReport.text = item.tag
                textDateItemReport.text =
                    if (updatedTime <= 0) item.createdTime.convertToDate("EEEE, dd MMMM yyyy HH:mm") else "[updated]\n${
                        updatedTime.convertToDate("EEEE, dd MMMM yyyy HH:mm")
                    }"
            }
        }
    }
}
