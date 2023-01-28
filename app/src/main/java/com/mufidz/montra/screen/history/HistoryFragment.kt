package com.mufidz.montra.screen.history

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.HistoryFragmentBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.intention.ReportAction
import com.mufidz.montra.screen.home.ReportAdapter
import com.mufidz.montra.screen.home.ReportListener
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.viewBinding
import com.mufidz.montra.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<HistoryFragmentBinding, ReportViewModel>(R.layout.history_fragment), ReportListener {

    private val reportAdapter by lazy { ReportAdapter(true) }

    override val viewModel: ReportViewModel by viewModels()

    override val binding: HistoryFragmentBinding by viewBinding()

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
                    val position = viewHolder.adapterPosition
                    if (position >= 0) {
                        var isConfirmDelete = true
                        val adapter = binding.rvHistory.adapter as ReportAdapter
                        val report = adapter.list[position]
                        adapter.removeAt(position)
                        snackbar("Berhasil dihapus", true, "Batal") {
                            adapter.updateAt(position, report)
                            isConfirmDelete = false
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isConfirmDelete) {
                                viewModel.execute(ReportAction.DeleteReportById(report.id))
                            }
                        }, 3000)
                    }
                }
            })

        with(binding) {
            appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                collapse.title =
                    if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) "History" else ""
            }
            toolbar.apply {
                initToolbar(this@HistoryFragment)
            }
            rvHistory.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = reportAdapter.apply {
                    onItemListener = this@HistoryFragment
                }
                itemTouchHelper.attachToRecyclerView(this)
            }
            imgNoData.visibility = View.GONE
        }

        viewModel.apply {
            execute(ReportAction.GetHomeData)
            viewState.observe(viewLifecycleOwner) {
                reportAdapter.setData(it.listReport)
                with(binding) {
                    imgNoData.visibility = if (it.listReport.isEmpty()) View.VISIBLE else View.GONE
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
                    }
                }
            }
        }
    }

    override fun onItemClick(data: Report) {
        bundleOf("report" to data).also {
            intent(R.id.addReportFragment, it)
        }
    }

    override fun getItemCount(count: Int) {
        binding.imgNoData.visibility = if (count == 0) View.VISIBLE else View.GONE
    }

    override fun onBalanceVisibilityChange(isVisible: Boolean) {}
}