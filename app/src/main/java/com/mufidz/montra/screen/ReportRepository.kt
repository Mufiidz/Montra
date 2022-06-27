package com.mufidz.montra.screen

import com.mufidz.montra.entity.Report
import javax.inject.Inject

interface ReportRepository {
    suspend fun addReport(report: Report): AddReportDataResult
    suspend fun getListReport(): ReportListDataResult
    suspend fun deleteById(id: Int): DeleteReportDataResult
    suspend fun updateReport(report: Report): AddReportDataResult
}

class ReportRepositoryImpl @Inject constructor(
    private val addReportUseCase: AddReportUseCase,
    private val listReportUseCase: ListReportUseCase,
    private val deleteByIdUseCase: DeleteByIdUseCase,
    private val updateUseCase: UpdateUseCase
) :
    ReportRepository {
    override suspend fun addReport(report: Report): AddReportDataResult =
        addReportUseCase.getResult(report)

    override suspend fun getListReport(): ReportListDataResult = listReportUseCase.getResult(null)

    override suspend fun deleteById(id: Int): DeleteReportDataResult =
        deleteByIdUseCase.getResult(id)

    override suspend fun updateReport(report: Report): AddReportDataResult =
        updateUseCase.getResult(report)
}