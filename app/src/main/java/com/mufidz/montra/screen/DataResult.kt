package com.mufidz.montra.screen

import com.mufidz.montra.base.UseCaseResult
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