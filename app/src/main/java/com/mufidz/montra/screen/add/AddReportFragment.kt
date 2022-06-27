package com.mufidz.montra.screen.add

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentAddReportBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.screen.ReportAction
import com.mufidz.montra.screen.ReportViewModel
import com.mufidz.montra.utils.formattedEditText
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.setErrorNot
import com.mufidz.montra.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReportFragment :
    BaseFragment<FragmentAddReportBinding, ReportViewModel>(R.layout.fragment_add_report) {

    private var isIncome = true

    private var isUpdate = false

    private var report: Report? = null

    override val viewModel: ReportViewModel by viewModels()

    override val binding: FragmentAddReportBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        report = arguments?.getParcelable("report")
        isUpdate = report != null
        val amount = report?.amount ?: 0
        val isIncomeReport = report?.isIncome ?: true

        with(binding) {
            toolbar.apply {
                initToolbar(this@AddReportFragment)
                title = if (isUpdate) "Update Report" else "Add Report"
            }
            toggleButton.apply {
                clearChecked()
                check(if (isIncomeReport) R.id.btn_pemasukan_report else R.id.btn_pengeluaran_report)
                addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        isIncome = checkedId != R.id.btn_pengeluaran_report
                    }
                }
            }
            edtTitleReport.apply {
                setText(report?.title)
                doAfterTextChanged {
                    textfieldTitleReport.setErrorNot(it.isNullOrEmpty())
                }
            }
            edtAmountReport.apply {
                setText(amount.toString())
                formattedEditText()
                doAfterTextChanged {
                    if (it.isNullOrEmpty()) setText("0")
                    textfieldAmountReport.setErrorNot(it.toString() == "0")
                }
            }
            edtDescReport.apply {
                setText(report?.comment ?: "")
            }
            textTagReport.apply {
                setText(report?.tag ?: "Pilih Tag")
                (this as MaterialAutoCompleteTextView).setSimpleItems(
                    arrayOf(
                        "Dana",
                        "Tabungan Kaleng",
                        "Dompet"
                    )
                )
            }


            btnSubmitReport.apply {
                text = if (isUpdate) "Update" else "Submit"
                setOnClickListener { onSubmitReport() }
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            it.message?.let { msg -> snackbar(msg, false) }
            it.errorMsg?.let { msg -> snackbar(msg, true) }
            Log.d("TAG", "isLoading: ${it.isLoading}")
        }
    }

    private fun onSubmitReport() {
        with(binding) {
            val title = edtTitleReport.text?.trim().toString()
            val edtTxtAmount =
                if (edtAmountReport.text.isNullOrEmpty()) "0" else edtAmountReport.text.toString()
            val amount = edtTxtAmount.replace(",".toRegex(), "").toInt()
            val desc = edtDescReport.text?.trim().toString()
            val tag = textTagReport.text.trim().toString()
            val isTagged = tag.isEmpty() || tag.equals(
                "pilih tag",
                true
            )

            when {
                title.isEmpty() -> textfieldTitleReport.setErrorNot(title.isEmpty())
                amount == 0 -> textfieldAmountReport.setErrorNot(amount == 0)
                isTagged -> textfieldTagReport.setErrorNot(isTagged)
                else -> {
                    val time = System.currentTimeMillis()
                    if (isUpdate) {
                        Report(
                            id = report?.id ?: 0,
                            title = title,
                            amount = amount,
                            isIncome = isIncome,
                            tag = tag,
                            createdTime = report?.createdTime ?: time,
                            updatedTime = time,
                            comment = desc
                        ).also {
                            viewModel.execute(ReportAction.UpdateReport(it))
                        }
                    } else {
                        Report(
                            title = title,
                            amount = amount,
                            isIncome = isIncome,
                            tag = tag,
                            createdTime = time,
                            comment = desc
                        ).also {
                            viewModel.execute(ReportAction.AddReport(it))
                        }
                    }
                }
            }
        }
    }
}