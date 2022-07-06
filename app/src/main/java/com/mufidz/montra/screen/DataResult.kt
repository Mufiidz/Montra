package com.mufidz.montra.screen

import com.mufidz.montra.base.UseCaseResult
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Report

sealed class AddReportDataResult : UseCaseResult() {
    data class Success(val message: String) : AddReportDataResult()
    data class Failed(val errorMsg: String) : AddReportDataResult()
}

sealed class ReportListDataResult : UseCaseResult() {
    data class Success(val listReport: List<Report>) : ReportListDataResult()
    data class Failed(val message: String) : ReportListDataResult()
}

sealed class DeleteReportDataResult : UseCaseResult() {
    data class Success(val data: Int) : DeleteReportDataResult()
    data class Failed(val message: String) : DeleteReportDataResult()
}

sealed class DashboardDataResult : UseCaseResult() {
    data class Success(val dashboard: Dashboard) : DashboardDataResult()
    data class Failed(val message: String) : DashboardDataResult()
}

sealed class NameDataResult : UseCaseResult() {
    data class Success(val name : String) : NameDataResult()
}

sealed class TagDataResult : UseCaseResult() {
    data class Success(val listTag : MutableList<String>) : TagDataResult()
}