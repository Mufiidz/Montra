package com.mufidz.montra.screen.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentAddReportBinding
import com.mufidz.montra.entity.Report
import com.mufidz.montra.intention.PreferencesAction
import com.mufidz.montra.intention.ReportAction
import com.mufidz.montra.utils.*
import com.mufidz.montra.viewmodel.PreferencesViewModel
import com.mufidz.montra.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class AddReportFragment :
    BaseFragment<FragmentAddReportBinding, ReportViewModel>(R.layout.fragment_add_report) {

    private var isIncome = true

    private var isUpdate = false

    private var report: Report? = null

    private val args by navArgs<AddReportFragmentArgs>()

    private val prefViewModel by viewModels<PreferencesViewModel>()

    override val viewModel: ReportViewModel by viewModels()

    override val binding: FragmentAddReportBinding by viewBinding()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        report = arguments?.getParcelable("report")
        val isNewIncome = args.isIncome
        isUpdate = report != null
        val amount = report?.amount ?: 0
        val type = args.type

        val isIncomeReport = if (!type.isNullOrEmpty()) {
            type.lowercase(Locale.getDefault()) != "outcome"
        } else {
            if (!isUpdate) isNewIncome else report?.isIncome ?: true
        }

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
            }
            txtUpdated.apply {
                val updateTime = report?.updatedTime ?: 0
                if (updateTime > 0) {
                    visibility = View.VISIBLE
                    text =
                        "Created : ${report?.createdTime?.convertToDate("EEEE, dd MMMM yyyy HH:mm")}\nLast Updated : ${
                            updateTime.convertToDate(
                                "EEEE, dd MMMM yyyy HH:mm"
                            )
                        }"
                }
            }
            btnSubmitReport.apply {
                text = if (isUpdate) "Update" else "Submit"
                setOnClickListener { onSubmitReport() }
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            it.message?.let { msg -> snackbar(msg, false) }
            it.errorMsg?.let { msg -> snackbar(msg, true) }
            if (it.isSuccess) {
                findNavController().navigateUp()
            }
        }

        prefViewModel.apply {
            prefViewModel.execute(PreferencesAction.GetTag)
            viewState.observe(viewLifecycleOwner) {
                Timber.d(it.listTag.toTypedArray().toString())
                binding.textTagReport.apply {
                    (this as MaterialAutoCompleteTextView).setSimpleItems(
                        it.listTag.toTypedArray()
                    )
                }
            }
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
            isIncome = toggleButton.checkedButtonId != R.id.btn_pengeluaran_report

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
                            Timber.d(it.toString())
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
                            Timber.d(it.toString())
                            viewModel.execute(ReportAction.AddReport(it))
                        }
                    }
                }
            }
        }
    }
}