package com.mufidz.montra.screen.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseSection
import com.mufidz.montra.databinding.ItemDashboardHomeBinding
import com.mufidz.montra.databinding.ItemHeaderHomeBinding
import com.mufidz.montra.databinding.ItemMainHomeBinding
import com.mufidz.montra.databinding.ItemProductHomeBinding
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Product
import com.mufidz.montra.entity.Report
import com.mufidz.montra.screen.home.viewholder.DashboardHomeViewHolder
import com.mufidz.montra.screen.home.viewholder.HeaderHomeViewHolder
import com.mufidz.montra.screen.home.viewholder.MainHomeViewHolder
import com.mufidz.montra.screen.home.viewholder.ProductHomeViewHolder
import com.mufidz.montra.utils.inflate

class HomeAdapter : BaseSection<HomeSection>() {

    var listener : ReportListener? = null

    override val section: MutableList<HomeSection> = HomeSection.getLayout()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HomeSection.HEADER_HOME -> HeaderHomeViewHolder(
                ItemHeaderHomeBinding.bind(parent.inflate(R.layout.item_header_home))
            )
            HomeSection.DASHBOARD_HOME -> DashboardHomeViewHolder(
                ItemDashboardHomeBinding.bind(parent.inflate(R.layout.item_dashboard_home)),
                listener
            )
            HomeSection.PRODUCT_HOME -> ProductHomeViewHolder(
                ItemProductHomeBinding.bind(parent.inflate(R.layout.item_product_home))
            )
            HomeSection.MAIN_HOME -> MainHomeViewHolder(
                ItemMainHomeBinding.bind(parent.inflate(R.layout.item_main_home)),
                listener
            )
            else -> throw IllegalArgumentException("Unknown View")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            when (this) {
                is HeaderHomeViewHolder -> {
                    val data = section[position] as HeaderHome
                    bind(data.name)
                }
                is ProductHomeViewHolder -> {
                    val data = section[position] as ProductHome
                    bind(data.listProduct)
                }
                is DashboardHomeViewHolder -> {
                    val data = section[position] as DashboardHome
                    data.dashboard?.let { bind(data) }
                }
                is MainHomeViewHolder -> {
                    val data = section[position] as MainHome
                    data.list?.let { bind(it) }
                }
            }
        }
    }

    fun setName(name : String) {
        getSectionPosition(HomeSection.HEADER_HOME)?.let {
            section[it] = HomeSection.getNameHome(name)
            notifyDataSetChanged()
        }
    }

    fun setListProduct(list: List<Product>) {
        getSectionPosition(HomeSection.PRODUCT_HOME)?.let {
            section[it] = HomeSection.getProductHome(list)
            notifyDataSetChanged()
        }
    }

    fun setListReport(list: MutableList<Report>) {
        getSectionPosition(HomeSection.MAIN_HOME)?.let {
            section[it] = HomeSection.getMainHome(list)
            notifyDataSetChanged()
        }
    }

    fun setDashboard(dashboard: Dashboard, isVisibleBalance: Boolean) {
        getSectionPosition(HomeSection.DASHBOARD_HOME)?.let {
            section[it] = HomeSection.getDashboardHome(dashboard, isVisibleBalance)
            notifyDataSetChanged()
        }
    }
}