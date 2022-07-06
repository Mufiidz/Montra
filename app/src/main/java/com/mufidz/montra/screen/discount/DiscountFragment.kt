package com.mufidz.montra.screen.discount

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.DiscountFragmentBinding
import com.mufidz.montra.utils.*

class DiscountFragment :
    BaseFragment<DiscountFragmentBinding, DiscountViewModel>(R.layout.discount_fragment) {

    private var result : Int = 0

    private var listDiscount = mutableListOf<Int>()

    private val discountAdapter by lazy { DiscountAdapter() }

    override val viewModel: DiscountViewModel by viewModels()

    override val binding: DiscountFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.apply {
                initToolbar(this@DiscountFragment)
            }
            edtPrice.apply {
                formattedEditText()
                doAfterTextChanged {
                    if (it.isNullOrEmpty()) setText("0")
                    textfieldPrice.setErrorNot(it.toString() == "0")
                }
            }
            edtDiscount.apply {
                doAfterTextChanged {
                    if (it.isNullOrEmpty()) setText("0")
                    if (it.toString().toInt() > 100) setText("100")
                }
            }
            btnAddDiscount.setOnClickListener {
                val discount = edtDiscount.text?.trim().toString().toInt()
                if (discount > 0) {
                    viewModel.addDiscount(discount)
                    rvDiscount.smoothScrollToPosition(discountAdapter.itemCount)
                    edtDiscount.setText("0")
                }
            }
            rvDiscount.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = discountAdapter
                itemTouchHelper.attachToRecyclerView(this)
            }
            btnCount.setOnClickListener {
                val price = edtPrice.text?.trim().toString().replace(",".toRegex(), "").toInt()
                val desc = "The result of ${price.toRp()} has been discounted with ${
                    listDiscount.joinToString(separator = "%, ", postfix = "%")
                }"
                if (price > 0) {
                    viewModel.getResult(price)
                    intent(R.id.resultDiscountBottomSheet, bundleOf("result" to result, "desc" to desc))
                }
            }

            setFragmentResultListener(ResultDiscountBottomSheet.RESULT_KEY) { _, bundle ->
                val isReset = bundle.getBoolean("isReset")
                if (isReset) {
                    edtPrice.setText("0")
                    edtDiscount.setText("0")
                    viewModel.deleteAllDiscount()
                }
            }
        }

        with(viewModel) {
            getListDiscount.observe(viewLifecycleOwner) {
                discountAdapter.list = it
                listDiscount = it
            }
            getPriceResult.observe(viewLifecycleOwner) {
                result = it
            }
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
                    val adapter = binding.rvDiscount.adapter as DiscountAdapter
                    val discount = adapter.list[position]
                    adapter.removeAt(position)
                    snackbar("Discount $discount% Berhasil dihapus", true, "Batal") {
                        adapter.updateAt(position, discount)
                    }
                }
            }
        })
}