package com.mufidz.montra.screen.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentHomeBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.screen.ReportAction
import com.mufidz.montra.screen.ReportViewModel
import com.mufidz.montra.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, ReportViewModel>(R.layout.fragment_home),
    HomeListener {

    private var amount = 0
    private var income = 0
    private var outcome = 0

    private val reportAdapter by lazy { ReportAdapter() }

    override val viewModel: ReportViewModel by viewModels()

    override val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    var isConfirmDelete = true
                    val adapter = binding.listItem.adapter as ReportAdapter
                    val report = adapter.list[viewHolder.adapterPosition]
                    adapter.removeAt(viewHolder.adapterPosition)
                    snackbar("Berhasil dihapus", false, "Batal") {
                        adapter.updateAt(viewHolder.adapterPosition, report)
                        isConfirmDelete = false
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isConfirmDelete) {
                            viewModel.execute(ReportAction.DeleteReportById(report.id))
                        }
                    }, 3000)
                }
            })

        with(binding) {
            appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                collapse.title =
                    if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) getString(R.string.app_name) else ""
            }
            fabAddReport.setOnClickListener {
                intent(R.id.addReportFragment, null)
            }
            listItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = reportAdapter.apply {
                    onItemListener = this@HomeFragment
                }
                itemTouchHelper.attachToRecyclerView(this)
            }
        }

        viewModel.apply {
            execute(ReportAction.GetListReport)
            viewState.observe(viewLifecycleOwner) {
                binding.shimmer.apply {
                    if (it.isLoading) {
                        visibility = View.VISIBLE
                        startShimmer()
                    } else {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }
                reportAdapter.setData(it.listReport)
            }
        }
    }

    override fun onItemClick(data: Report) {
        bundleOf("report" to data).also {
            intent(R.id.addReportFragment, it)
        }
    }

    override fun getItem(report: Report) {
        amount = if (report.isIncome) amount + report.amount else amount - report.amount
        if (report.isIncome) income += report.amount else outcome += report.amount
        Log.d("TAG", "Amount: $amount Income: $income Outcome: $outcome")
    }

    override fun getItemCount(count: Int) {
        binding.imgNoData.visibility = if (count == 0) View.VISIBLE else View.GONE
    }
}