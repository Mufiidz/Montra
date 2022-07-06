package com.mufidz.montra.usecase

import com.mufidz.montra.base.BaseUseCase
import com.mufidz.montra.datamanager.DataManager
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Report
import com.mufidz.montra.screen.*
import com.mufidz.montra.utils.DispatcherProvider
import com.mufidz.montra.utils.ResultData
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddReportUseCase @Inject constructor(
    dispatcher: DispatcherProvider,
    private val dataManager: DataManager
) : BaseUseCase<Report, ResultData<Void>, AddReportDataResult>(dispatcher.dispatcherIO()) {

    override suspend fun execute(param: Report): ResultData<Void> = dataManager.addReport(param)

    override suspend fun ResultData<Void>.transformToUseCaseResult(): AddReportDataResult =
        when (this) {
            is ResultData.Error -> AddReportDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> AddReportDataResult.Success("Berhasil")
        }
}

class ListReportUseCase @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val dataManager: DataManager
) : BaseUseCase<Void?, ResultData<List<Report>>, ReportListDataResult>(dispatcher.dispatcherIO()) {

    override suspend fun execute(param: Void?): ResultData<List<Report>> =
        dataManager.getAllReport()

    override suspend fun ResultData<List<Report>>.transformToUseCaseResult(): ReportListDataResult =
        when (this) {
            is ResultData.Error -> ReportListDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> {
                val data =
                    withContext(dispatcher.default()) { value.sortedByDescending { it.createdTime } }
                ReportListDataResult.Success(data)
            }
        }
}

class DeleteByIdUseCase @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val dataManager: DataManager
) : BaseUseCase<Int, ResultData<Int>, DeleteReportDataResult>(dispatcher.dispatcherIO()) {

    override suspend fun execute(param: Int): ResultData<Int> = dataManager.deleteReportById(param)

    override suspend fun ResultData<Int>.transformToUseCaseResult(): DeleteReportDataResult =
        when (this) {
            is ResultData.Error -> DeleteReportDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> {
                val data = withContext(dispatcher.default()) { value }
                DeleteReportDataResult.Success(data)
            }
        }
}

class UpdateUseCase @Inject constructor(
    dispatcher: DispatcherProvider,
    private val dataManager: DataManager
) : BaseUseCase<Report, ResultData<Void>, AddReportDataResult>(dispatcher.dispatcherIO()) {

    override suspend fun execute(param: Report): ResultData<Void> =
        dataManager.updateReport(param)

    override suspend fun ResultData<Void>.transformToUseCaseResult(): AddReportDataResult =
        when (this) {
            is ResultData.Error -> AddReportDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> AddReportDataResult.Success("Berhasil diubah")
        }
}

class DashboardUseCase @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val dataManager: DataManager
) : BaseUseCase<Nothing?, ResultData<Dashboard>, DashboardDataResult>(dispatcher.dispatcherIO()) {

    override suspend fun execute(param: Nothing?): ResultData<Dashboard> =
        dataManager.getDashboard()

    override suspend fun ResultData<Dashboard>.transformToUseCaseResult(): DashboardDataResult =
        when (this) {
            is ResultData.Error -> DashboardDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> {
                val data = withContext(dispatcher.default()) { value }
                DashboardDataResult.Success(data)
            }
        }

}