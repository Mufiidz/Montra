package com.mufidz.montra.screen.discount

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.mufidz.montra.base.BaseBottomSheet
import com.mufidz.montra.databinding.BottomsheetResultDiscountBinding
import com.mufidz.montra.screen.plan.MoneyPlanFragment
import com.mufidz.montra.utils.toRp
import com.mufidz.montra.utils.viewBinding

class ResultDiscountBottomSheet :
    BaseBottomSheet<BottomsheetResultDiscountBinding>() {

    companion object {
        const val RESULT_KEY = "RESULT_DISCOUNT"
    }

    override val binding: BottomsheetResultDiscountBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = arguments?.getInt("result") ?: 0
        val desc = arguments?.getString("desc") ?: "-"
        with(binding) {
            txtResult.text = result.toRp()
            txtDesc.text = desc
            btnReset.setOnClickListener {
                setFragmentResult(RESULT_KEY, bundleOf("isReset" to true))
                setFragmentResult(MoneyPlanFragment.RESULT_KEY, bundleOf("isReset" to true))
                findNavController().navigateUp()
            }
            btnDismiss.setOnClickListener { dismissAllowingStateLoss() }
        }
    }

}