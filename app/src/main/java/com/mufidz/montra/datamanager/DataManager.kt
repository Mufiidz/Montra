package com.mufidz.montra.datamanager

import com.mufidz.montra.data.local.ReportDao
import com.mufidz.montra.entity.Dashboard
import com.mufidz.montra.entity.Report
import com.mufidz.montra.utils.ResultData
import javax.inject.Inject

interface DataManager {
    suspend fun addReport(report: Report): ResultData<Void>
    suspend fun updateReport(report: Report): ResultData<Void>
    suspend fun deleteAllReport(): ResultData<Int>
    suspend fun deleteReportById(id: Int): ResultData<Int>
    suspend fun getAllReport(): ResultData<List<Report>>
    suspend fun getDashboard(): ResultData<Dashboard>
}

class DataManagerImpl @Inject constructor(private val reportDao: ReportDao) : DataManager {

    override suspend fun addReport(report: Report): ResultData<Void> {
        return try {
            ResultData.Success(reportDao.insert(report))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun updateReport(report: Report): ResultData<Void> {
        return try {
            ResultData.Success(reportDao.update(report))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun deleteAllReport(): ResultData<Int> {
        return try {
            ResultData.Success(reportDao.deleteAll())
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun deleteReportById(id: Int): ResultData<Int> {
        return try {
            ResultData.Success(reportDao.deleteById(id))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getAllReport(): ResultData<List<Report>> {
        return try {
            ResultData.Success(reportDao.getAll())
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun getDashboard(): ResultData<Dashboard> {
        return try {
            var amount = 0
            var income = 0
            var outcome = 0
            val listReport = reportDao.getAll()
            for (report in listReport) {
                val isIncome = report.isIncome
                if (isIncome) {
                    amount += report.amount
                    income += report.amount
                } else {
                    amount -= report.amount
                    outcome += report.amount
                }
            }
            val dashboard = Dashboard(amount, income, outcome)
            ResultData.Success(dashboard)
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}