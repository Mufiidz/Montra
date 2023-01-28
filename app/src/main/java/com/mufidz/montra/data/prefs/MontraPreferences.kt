package com.mufidz.montra.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MontraPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val BALANCE_VISIBILITY = "balance_visibility"
        const val TAG_REPORT = "tag_report"
        const val NAME = "name"
    }

    var balanceVisibility: Boolean
        get() = sharedPreferences.getBoolean(BALANCE_VISIBILITY, false)
        set(value) = sharedPreferences.edit { putBoolean(BALANCE_VISIBILITY, value) }

    var tags: MutableList<String>
        get() {
            val tags =
                sharedPreferences.getString(TAG_REPORT, null)
            return if (tags.isNullOrEmpty()) mutableListOf("Dompet") else Json.decodeFromString(tags)
        }
        set(value) {
            val list = if (value.isEmpty()) mutableListOf("Dompet") else value
            val tags = Json.encodeToString(list)
            sharedPreferences.edit {
                putString(TAG_REPORT, tags)
            }
        }
    var name: String
        get() = sharedPreferences.getString(NAME, "USER") ?: "USER"
        set(value) = sharedPreferences.edit { putString(NAME, value) }
}