package com.mufidz.montra.screen.home

import com.mufidz.montra.base.Section
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Product
import com.mufidz.montra.entity.Report

sealed class HomeSection : Section() {
    companion object {
        const val HEADER_HOME = 1
        const val DASHBOARD_HOME = 2
        const val PRODUCT_HOME = 3
        const val MAIN_HOME = 4

        @JvmStatic
        fun getLayout(): MutableList<HomeSection> = mutableListOf(
            HeaderHome(),
            DashboardHome(),
            ProductHome(),
            MainHome()
        )

        fun getNameHome(name : String) : HomeSection = HeaderHome(name)

        fun getDashboardHome(dashboard: Dashboard) : HomeSection = DashboardHome(dashboard)

        fun getProductHome(listProduct: List<Product>) = ProductHome(listProduct)

        fun getMainHome(list: MutableList<Report>) = MainHome(list)
    }
}

data class HeaderHome(val name : String = "") : HomeSection() {
    override val viewType: Int get() = HEADER_HOME
}

data class DashboardHome(val dashboard: Dashboard? = null) : HomeSection() {
    override val viewType: Int get() = DASHBOARD_HOME
}

data class ProductHome(val listProduct: List<Product> = emptyList()) : HomeSection() {
    override val viewType: Int get() = PRODUCT_HOME
}

data class MainHome(val list: MutableList<Report>? = null) : HomeSection() {
    override val viewType: Int get() = MAIN_HOME
}