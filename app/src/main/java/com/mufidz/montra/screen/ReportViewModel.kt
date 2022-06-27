package com.mufidz.montra.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mufidz.montra.base.BaseViewModel
import com.mufidz.montra.base.UseCaseResult
import com.mufidz.montra.utils.callUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository) :
    BaseViewModel<ReportViewState, ReportAction>(ReportViewState()) {
    override fun renderViewState(result: UseCaseResult?): ReportViewState =
        when (result) {
            is AddReportDataResult -> result.mapAddReport()
            is ReportListDataResult -> result.mapListReport()
            is DeleteReportDataResult -> result.mapDeleteReport()
            else -> getCurrentViewState()
        }

    override fun handleAction(action: ReportAction): LiveData<UseCaseResult> =
        liveData(viewModelScope.coroutineContext) {
            when (action) {
                is ReportAction.AddReport -> {
                    val report = action.report
                    delay(250)
                    coroutineScope {
                        launch {
                            callUseCase(reportRepository.addReport(report))
                        }
                    }
                }
                ReportAction.GetListReport -> {
                    delay(250)
                    coroutineScope {
                        launch {
                            callUseCase(reportRepository.getListReport())
                        }
                    }
                }
                is ReportAction.DeleteReportById -> {
                    delay(250)
                    val id = action.id
                    coroutineScope {
                        launch {
                            callUseCase(reportRepository.deleteById(id))
                        }
                    }
                }
                is ReportAction.UpdateReport -> {
                    val report = action.report
                    delay(250)
                    coroutineScope {
                        launch {
                            callUseCase(reportRepository.updateReport(report))
                        }
                    }
                }
            }
        }

    private fun AddReportDataResult.mapAddReport(): ReportViewState = when (this) {
        is AddReportDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = errorMsg
        )
        is AddReportDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            message = message
        )
    }

    private fun ReportListDataResult.mapListReport(): ReportViewState = when (this) {
        is ReportListDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = message
        )
        is ReportListDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            listReport = listReport.toMutableList()
        )
    }

    private fun DeleteReportDataResult.mapDeleteReport() : ReportViewState = when (this) {
        is DeleteReportDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = message
        )
        is DeleteReportDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            message = "Berhasil terhapus id $data"
        )
    }
}