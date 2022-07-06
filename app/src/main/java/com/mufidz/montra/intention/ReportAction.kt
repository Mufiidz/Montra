package com.mufidz.montra.intention

import android.content.Context
import com.mufidz.montra.base.ViewAction
import com.mufidz.montra.base.ViewState
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Report

data class ReportViewState(
    val listReport: MutableList<Report> = mutableListOf(),
    val dashboard: Dashboard = Dashboard(),
    val isLoading: Boolean = true,
    val message: String? = null,
    val errorMsg: String? = null,
    val name : String = "-",
    val isSuccess : Boolean = false
) : ViewState

sealed class ReportAction : ViewAction {
    data class AddReport(val report: Report) : ReportAction()
    data class UpdateReport(val report: Report) : ReportAction()
    data class DeleteReportById(val id: Int) : ReportAction()
    object GetHomeData : ReportAction()
}