package com.mufidz.montra.screen.plan

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentEditMoneyplanBinding
import com.mufidz.montra.entity.MoneyPlan
import com.mufidz.montra.utils.formattedEditText
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.toRp
import com.mufidz.montra.utils.viewBinding

class EditMoneyPlanFragment :
    BaseFragment<FragmentEditMoneyplanBinding, MoneyPlanViewModel>(R.layout.fragment_edit_moneyplan) {

    private var quantity = 1

    private var price = 0

    private var isUpdate = false

    private val args by navArgs<EditMoneyPlanFragmentArgs>()

    companion object {
        const val RESULT_KEY = "RESULT_ADD_PLAN"
    }

    override val viewModel: MoneyPlanViewModel by viewModels()

    override val binding: FragmentEditMoneyplanBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moneyPlan = args.moneyPlan
        isUpdate = moneyPlan != null

        if (moneyPlan != null) {
            price = moneyPlan.amount
            quantity = moneyPlan.quantity
            viewModel.getTotal(price, quantity)
        }

        with(binding) {
            toolbar.apply {
                initToolbar(this@EditMoneyPlanFragment)
                title = if (isUpdate) "Update Money Plan" else "Add Money Plan"
            }
            edtTitle.apply {
                setText(moneyPlan?.title)
            }
            edtPrice.apply {
                setText((moneyPlan?.amount ?: 0).toString())
                formattedEditText()
                doAfterTextChanged {
                    if (it.isNullOrEmpty()) setText("0")
                    price = text?.trim().toString().replace(",".toRegex(), "").toInt()
                    viewModel.getTotal(price, quantity)
                }
            }
            edtQuantity.apply {
                setText((moneyPlan?.quantity ?: 0).toString())
                doOnTextChanged { text, _, _, _ ->
                    if (text.isNullOrEmpty() || text.trim().toString().toInt() < 1) setText("1")
                }
                doAfterTextChanged {
                    val dummyQty = if (it.isNullOrEmpty()) 1 else it.trim().toString().toInt()
                    val qty = if (dummyQty < 1) 1 else dummyQty
                    if (qty > 100) setText("99")
                    quantity = qty
                    viewModel.getTotal(price, quantity)
                }
            }

            btnAddPlan.apply {
                text = if (!isUpdate) "Add" else "Update"
                setOnClickListener {
                    val title = edtTitle.text.trim().toString()
                    val price = edtPrice.text?.trim().toString().replace(",".toRegex(), "").toInt()
                    val qty = edtQuantity.text?.trim().toString().toInt()
                    val total = price * qty

                    when {
                        title.isEmpty() -> edtTitle.error = "Tidak Boleh Kosong"
                        price < 1 -> edtPrice.error = "Tidak Boleh Kosong"
                        else -> {
                            MoneyPlan(title = title, amount = total, quantity = qty).also {
                                if (!isUpdate) {
                                    setFragmentResult(
                                        MoneyPlanFragment.RESULT_KEY,
                                        bundleOf("moneyPlan" to it, "isUpdate" to false)
                                    )
                                } else {
                                    moneyPlan?.let { plan ->
                                        it.id = plan.id
                                    }
                                    setFragmentResult(
                                        MoneyPlanFragment.RESULT_KEY,
                                        bundleOf("moneyPlan" to it, "isUpdate" to true)
                                    )
                                }

                            }
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }

        viewModel.getTotal.observe(viewLifecycleOwner) {
            binding.tvTotal.text = it.toRp()
        }

    }
}