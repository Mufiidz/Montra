package com.mufidz.montra.screen.home.viewholder

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemMainHomeBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.screen.home.ReportAdapter
import com.mufidz.montra.screen.home.ReportListener
import com.mufidz.montra.utils.slideLeftRightAnim

class MainHomeViewHolder(
    private val binding: ItemMainHomeBinding,
    private val listener: ReportListener?
) :
    BaseViewHolder<MutableList<Report>>(binding.root) {
    private val reportAdapter by lazy { ReportAdapter() }
    override fun bind(item: MutableList<Report>) {
        reportAdapter.setData(item)
        binding.apply {
            root.visibility = if (item.size != 0) View.VISIBLE else View.GONE
            btnSeeall.apply {
                visibility = if (item.size > 4) View.VISIBLE else View.GONE
                setOnClickListener {
                    it.findNavController()
                        .navigate(R.id.historyFragment, null, navOptions { slideLeftRightAnim() })
                }
            }
            listItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = reportAdapter.apply {
                    onItemListener = listener
                }
            }
        }
    }
}
