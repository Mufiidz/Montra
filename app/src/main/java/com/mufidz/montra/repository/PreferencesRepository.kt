package com.mufidz.montra.repository

import com.mufidz.montra.screen.NameDataResult
import com.mufidz.montra.screen.TagDataResult
import com.mufidz.montra.usecase.GetNameUseCase
import com.mufidz.montra.usecase.GetTagUseCase
import com.mufidz.montra.usecase.SetTagUseCase
import javax.inject.Inject

interface PreferencesRepository {
    suspend fun getName(): NameDataResult
    suspend fun setTag(listTag: MutableList<String>): TagDataResult
    suspend fun getTag(): TagDataResult
}

class PreferencesRepositoryImpl @Inject constructor(
    private val getNameUseCase: GetNameUseCase,
    private val setTagUseCase: SetTagUseCase,
    private val getTagUseCase: GetTagUseCase,
) : PreferencesRepository {

    override suspend fun getName(): NameDataResult =
        getNameUseCase.getResult(null)

    override suspend fun setTag(listTag: MutableList<String>): TagDataResult =
        setTagUseCase.getResult(listTag)

    override suspend fun getTag(): TagDataResult = getTagUseCase.getResult(null)
}