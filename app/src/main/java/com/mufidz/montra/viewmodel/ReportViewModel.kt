package com.mufidz.montra.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mufidz.montra.base.BaseViewModel
import com.mufidz.montra.base.UseCaseResult
import com.mufidz.montra.intention.ReportAction
import com.mufidz.montra.intention.ReportViewState
import com.mufidz.montra.repository.PreferencesRepository
import com.mufidz.montra.repository.ReportRepository
import com.mufidz.montra.screen.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<ReportViewState, ReportAction>(ReportViewState()) {
    override fun renderViewState(result: UseCaseResult?): ReportViewState =
        when (result) {
            is AddReportDataResult -> result.mapAddReport()
            is ReportListDataResult -> result.mapListReport()
            is DeleteReportDataResult -> result.mapDeleteReport()
            is DashboardDataResult -> result.mapDashboard()
            is NameDataResult -> result.mapNameResult()
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
                            emit(reportRepository.addReport(report))
                        }
                    }
                }
                ReportAction.GetHomeData -> {
                    delay(3000)
                    coroutineScope {
                        launch {
                            emit(preferencesRepository.getName())
                            emit(reportRepository.getListReport())
                            emit(reportRepository.getDashboard())
                        }
                    }
                }
                is ReportAction.DeleteReportById -> {
                    delay(250)
                    val id = action.id
                    coroutineScope {
                        launch {
                            emit(reportRepository.deleteById(id))
                        }
                    }
                }
                is ReportAction.UpdateReport -> {
                    val report = action.report
                    delay(250)
                    coroutineScope {
                        launch {
                            emit(reportRepository.updateReport(report))
                        }
                    }
                }
            }
        }

    private fun AddReportDataResult.mapAddReport(): ReportViewState = when (this) {
        is AddReportDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = errorMsg,
            isSuccess = false
        )
        is AddReportDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            message = message,
            isSuccess = true
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

    private fun DeleteReportDataResult.mapDeleteReport(): ReportViewState = when (this) {
        is DeleteReportDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = message
        )
        is DeleteReportDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            message = "Berhasil terhapus id $data"
        )
    }

    private fun DashboardDataResult.mapDashboard(): ReportViewState = when (this) {
        is DashboardDataResult.Failed -> getCurrentViewState().copy(
            isLoading = false,
            errorMsg = message
        )
        is DashboardDataResult.Success -> getCurrentViewState().copy(
            isLoading = false,
            dashboard = dashboard
        )
    }

    private fun NameDataResult.mapNameResult() : ReportViewState =
        when (this) {
            is NameDataResult.Success -> getCurrentViewState().copy(
                isLoading = false, name = name
            )
        }
}