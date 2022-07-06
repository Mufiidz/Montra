package com.mufidz.montra.datamanager

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface PreferencesDataManagerRepository {
    suspend fun getNameProfile(): String?
    suspend fun setTagReport(listTag: MutableList<String>)
    suspend fun getTagReport(): MutableList<String>
}

class PreferencesDataManagerRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) :
    PreferencesDataManagerRepository {

    companion object {
        const val NAME = "name"
        const val TAG_REPORT = "tag_report"
    }

    override suspend fun getNameProfile() = withContext(Dispatchers.IO) {
        sharedPreferences.getString(NAME, "USER")
    }

    override suspend fun setTagReport(listTag: MutableList<String>) = withContext(Dispatchers.IO) {
        val list = if (listTag.isEmpty()) mutableListOf("Dompet") else listTag
        val tags = Json.encodeToString(list)
        sharedPreferences.edit {
            putString(TAG_REPORT, tags)
        }
    }

    override suspend fun getTagReport(): MutableList<String> = withContext(Dispatchers.IO) {
        val tags = sharedPreferences.getString(TAG_REPORT, null)
        val list =
            if (tags.isNullOrEmpty()) mutableListOf("Dompet") else Json.decodeFromString(tags)
        list
    }

}