package com.mufidz.montra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mufidz.montra.entity.Report

@Database(entities = [Report::class], version = 1)
abstract class MontraDb : RoomDatabase() {
    abstract fun reportDao(): ReportDao

    companion object {
        private var INSTANCE: MontraDb? = null
        private const val DB_NAME = "montra.db"

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(MontraDb::class.java) {
            getInstance(context).also { INSTANCE = it }
        }

        private fun getInstance(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MontraDb::class.java,
            DB_NAME
        ).build()
    }
}