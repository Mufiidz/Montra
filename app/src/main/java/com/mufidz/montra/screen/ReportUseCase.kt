package com.mufidz.montra.screen

import com.mufidz.montra.base.BaseUseCase
import com.mufidz.montra.entity.Report
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
            is ResultData.Canceled -> AddReportDataResult.Failed(exception?.localizedMessage.orEmpty())
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
            is ResultData.Canceled -> ReportListDataResult.Failed(exception?.localizedMessage.orEmpty())
            is ResultData.Error -> ReportListDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> {
                val data = withContext(dispatcher.default()) { value }
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
            is ResultData.Canceled -> DeleteReportDataResult.Failed(exception?.localizedMessage.orEmpty())
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
            is ResultData.Canceled -> AddReportDataResult.Failed(exception?.localizedMessage.orEmpty())
            is ResultData.Error -> AddReportDataResult.Failed(exception.localizedMessage.orEmpty())
            is ResultData.Success -> AddReportDataResult.Success("Berhasil diubah")
        }
}