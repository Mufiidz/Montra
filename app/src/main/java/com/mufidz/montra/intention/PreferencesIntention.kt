package com.mufidz.montra.intention

import android.content.Context
import com.mufidz.montra.base.ViewAction
import com.mufidz.montra.base.ViewState

data class PreferencesViewState(
    val name : String = "-",
    val listTag: MutableList<String> = mutableListOf(),
    val isLoading : Boolean = true
) : ViewState

sealed class PreferencesAction : ViewAction {
    object GetName : PreferencesAction()
    data class SetTag(val listTag : MutableList<String>) : PreferencesAction()
    object GetTag : PreferencesAction()
}