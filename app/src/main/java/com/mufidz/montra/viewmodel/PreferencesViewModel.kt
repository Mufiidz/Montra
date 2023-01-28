package com.mufidz.montra.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mufidz.montra.R
import java.util.*

class PreferencesViewModel : ViewModel() {

    private val listTag = MutableLiveData(mutableListOf<String>())

    val getListTag: LiveData<MutableList<String>> = listTag

    fun addTag(tag: String) {
        listTag.value?.add(tag)
        listTag.value = listTag.value
    }

    fun addAllTag(tags: MutableList<String>) {
        listTag.value = tags
    }

    fun removeTag(tag: String) {
        listTag.value?.remove(tag)
        listTag.value = listTag.value
    }

    fun onMove(from: Int, to: Int) {
        listTag.value?.toMutableList()?.let {
            Collections.swap(it, from, to)
        }
        listTag.value = listTag.value
    }

    fun setTheme(chosenTheme: String, context: Context) {
        val themeValues = context.resources.getStringArray(R.array.mode)
        when (chosenTheme) {
            themeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            themeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            themeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}