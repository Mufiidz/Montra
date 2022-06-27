package com.mufidz.montra.data.local

import androidx.room.*
import com.mufidz.montra.entity.Report

@Dao
interface ReportDao {
    
    @Query("SELECT * FROM report")
    suspend fun getAll(): List<Report>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: Report) : Void

    @Update
    suspend fun update(report: Report) : Void

    @Query("DELETE FROM report WHERE id = :itemId")
    suspend fun deleteById(itemId: Int) : Int

    @Query("DELETE FROM report")
    suspend fun deleteAll() : Int
}