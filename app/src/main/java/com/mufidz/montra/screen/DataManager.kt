package com.mufidz.montra.screen

import com.mufidz.montra.data.local.ReportDao
import com.mufidz.montra.entity.Report
import com.mufidz.montra.utils.ResultData
import javax.inject.Inject

interface DataManager {
    suspend fun addReport(report: Report) : ResultData<Void>
    suspend fun updateReport(report: Report) : ResultData<Void>
    suspend fun deleteAllReport() : ResultData<Int>
    suspend fun deleteReportById(id: Int) : ResultData<Int>
    suspend fun getAllReport(): ResultData<List<Report>>
}

class DataManagerImpl @Inject constructor(private val reportDao: ReportDao) : DataManager {

    override suspend fun addReport(report: Report) : ResultData<Void> {
        return try {
            ResultData.Success(reportDao.insert(report))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    override suspend fun updateReport(report: Report) : ResultData<Void> {
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
}