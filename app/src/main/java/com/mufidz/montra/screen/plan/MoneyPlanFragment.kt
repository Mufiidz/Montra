package com.mufidz.montra.screen.plan

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.base.ItemListener
import com.mufidz.montra.databinding.FragmentMoneyPlanBinding
import com.mufidz.montra.entity.MoneyPlan
import com.mufidz.montra.utils.*

class MoneyPlanFragment :
    BaseFragment<FragmentMoneyPlanBinding, MoneyPlanViewModel>(R.layout.fragment_money_plan),
    ItemListener<MoneyPlan> {

    companion object {
        const val RESULT_KEY = "RESULT_Plan"
    }

    private var result: Int = 0

    private val planAdapter by lazy { MoneyPlanAdapter() }

    override val viewModel: MoneyPlanViewModel by viewModels()

    override val binding: FragmentMoneyPlanBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.apply {
                initToolbar(this@MoneyPlanFragment)
            }
            edtPrice.apply {
                formattedEditText()
                doAfterTextChanged {
                    if (it.isNullOrEmpty()) setText("0")
                    textfieldPrice.setErrorNot(it.toString() == "0")
                }
            }
            rvPlan.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = planAdapter.apply { onItemListener = this@MoneyPlanFragment }
                itemTouchHelper.attachToRecyclerView(this)
            }
            btnAddPlan.setOnClickListener {
                val title = edtTitle.text.toString().trim()
                val price = edtPrice.text?.trim().toString().filter { it.isDigit() }.toInt()

                when {
                    title.isEmpty() && price <= 0 -> intent(R.id.editMoneyPlanFragment)
                    title.isEmpty() || price <= 0 -> {
                        textfieldTitle.setErrorNot(title.isEmpty())
                        textfieldPrice.setErrorNot(price <= 0)
                    }
                    else -> {
                        MoneyPlan(title = title, amount = price).also {
                            viewModel.addMoneyPlan(it)
                        }
                        edtTitle.text?.clear()
                        edtPrice.setText(getString(R.string._0))
                    }
                }
            }
            btnCount.setOnClickListener {
                val desc = "The result of your money plan"
                viewModel.getResult()
                intent(R.id.resultDiscountBottomSheet, bundleOf("result" to result, "desc" to desc))
            }
        }

        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            bundle.parcelable<MoneyPlan>("moneyPlan")?.let {
                val isUpdate = bundle.getBoolean("isUpdate")
                if (isUpdate) viewModel.updateMoneyPlan(it) else viewModel.addMoneyPlan(it)
            }
            bundle.getBoolean("isReset").also {
                if (it) {
                    viewModel.deleteAllPlan()
                }
            }
        }

        with(viewModel) {
            getListMoneyPlan.observe(viewLifecycleOwner) {
                planAdapter.setData(it)
            }
            getPriceResult.observe(viewLifecycleOwner) { result = it }
        }
    }

    private val itemTouchHelper =
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
                    val adapter = binding.rvPlan.adapter as MoneyPlanAdapter
                    val moneyPlan = adapter.list[position]
                    adapter.removeAt(position)
                    snackbar("${moneyPlan.title} Berhasil dihapus", true, "Batal") {
                        adapter.updateAt(position, moneyPlan)
                        isConfirmDelete = false
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isConfirmDelete) {
                            viewModel.deleteMoneyPlan(moneyPlan)
                        }
                    }, 3000)
                }
            }
        })

    override fun onItemClick(data: MoneyPlan) {
        findNavController().navigate(
            R.id.editMoneyPlanFragment,
            bundleOf("moneyPlan" to data),
            navOptions { slideLeftRightAnim() })
    }
}