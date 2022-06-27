package com.mufidz.montra.base

abstract class UseCaseResult

abstract class ViewState

abstract class ViewSideEffect : ViewState()

abstract class Section {
    abstract val viewType: Int
}

interface ViewAction

interface StatelessViewAction : ViewAction

interface ItemClick