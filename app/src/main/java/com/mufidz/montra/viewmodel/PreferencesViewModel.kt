package com.mufidz.montra.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewModel
import com.mufidz.montra.base.UseCaseResult
import com.mufidz.montra.intention.PreferencesAction
import com.mufidz.montra.intention.PreferencesViewState
import com.mufidz.montra.repository.PreferencesRepository
import com.mufidz.montra.screen.NameDataResult
import com.mufidz.montra.screen.TagDataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<PreferencesViewState, PreferencesAction>(PreferencesViewState()) {

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

    override fun renderViewState(result: UseCaseResult?): PreferencesViewState =
        when (result) {
            is NameDataResult -> result.mapNameResult()
            is TagDataResult -> result.mapTagResult()
            else -> getCurrentViewState()
        }

    override fun handleAction(action: PreferencesAction): LiveData<UseCaseResult> =
        liveData(viewModelScope.coroutineContext) {
            when (action) {
                PreferencesAction.GetName -> {
                    coroutineScope {
                        launch {
                            emit(preferencesRepository.getName())
                        }
                    }
                }
                PreferencesAction.GetTag -> {
                    coroutineScope {
                        launch {
                            emit(preferencesRepository.getTag())
                        }
                    }
                }
                is PreferencesAction.SetTag -> {
                    val tags = action.listTag
                    coroutineScope {
                        launch {
                            Timber.d(tags.toString())
                            emit(preferencesRepository.setTag(tags))
                        }
                    }
                }
            }
        }

    private fun NameDataResult.mapNameResult(): PreferencesViewState =
        when (this) {
            is NameDataResult.Success -> getCurrentViewState().copy(
                isLoading = false, name = name
            )
        }

    private fun TagDataResult.mapTagResult(): PreferencesViewState =
        when (this) {
            is TagDataResult.Success -> getCurrentViewState().copy(
                isLoading = false, listTag = listTag
            )
        }
}