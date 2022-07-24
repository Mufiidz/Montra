package com.mufidz.montra.screen.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentHomeBinding
import com.mufidz.montra.entity.Product
import com.mufidz.montra.entity.Report
import com.mufidz.montra.intention.ReportAction
import com.mufidz.montra.utils.viewBinding
import com.mufidz.montra.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, ReportViewModel>(R.layout.fragment_home), ReportListener {

    private val homeAdapter by lazy { HomeAdapter() }

    override val viewModel: ReportViewModel by viewModels()

    override val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            listItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter.apply { listener = this@HomeFragment }
            }
        }

        viewModel.apply {
            execute(ReportAction.GetHomeData)
            viewState.observe(viewLifecycleOwner) {
                val amount = it.dashboard.amount
                with(binding) {
                    if (it.isLoading) {
                        shimmer.apply {
                            visibility = View.VISIBLE
                            startShimmer()
                        }
                    } else {
                        shimmer.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }
                        listItem.scrollToPosition(0)
                    }
                    root.apply {
                        background = if (amount > 0) ContextCompat.getDrawable(
                            context,
                            R.drawable.gradient_home
                        ) else if (amount == 0) null else ContextCompat.getDrawable(
                            context,
                            R.drawable.gradient_home_error
                        )
                    }
                }

                homeAdapter.apply {
                    Timber.d(it.name)
                    setName(it.name)
                    setDashboard(it.dashboard)
                    setListProduct(getListProduct())
                    setListReport(it.listReport)
                }
            }
        }
    }

    private fun getListProduct(): List<Product> = listOf(
        Product("Income", R.id.addReportFragment, bundleOf("isIncome" to true)),
        Product("Money Plan", R.id.moneyPlanFragment),
        Product("Outcome", R.id.addReportFragment, bundleOf("isIncome" to false)),
        Product("Wishlist"),
        Product("History", R.id.historyFragment),
        Product("Discount Calculator", R.id.discountFragment),
    )

    override fun onItemClick(data: Report) {
        bundleOf("report" to data).also {
            intent(R.id.addReportFragment, it)
        }
    }

    override fun getItemCount(count: Int) {}
}