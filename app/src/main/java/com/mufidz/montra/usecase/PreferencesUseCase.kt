package com.mufidz.montra.usecase

import com.mufidz.montra.base.BaseUseCase
import com.mufidz.montra.datamanager.PreferencesDataManagerRepository
import com.mufidz.montra.screen.NameDataResult
import com.mufidz.montra.screen.TagDataResult
import com.mufidz.montra.utils.DispatcherProvider
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val sharedPreferencesRepository: PreferencesDataManagerRepository
) : BaseUseCase<Nothing?, String, NameDataResult>(dispatcherProvider.dispatcherIO()) {

    override suspend fun execute(param: Nothing?): String =
        sharedPreferencesRepository.getNameProfile().orEmpty()

    override suspend fun String.transformToUseCaseResult(): NameDataResult =
        NameDataResult.Success(this)
}

class SetTagUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val sharedPreferencesRepository: PreferencesDataManagerRepository
) : BaseUseCase<MutableList<String>, Unit, TagDataResult>(dispatcherProvider.dispatcherIO()) {

    private var tags = mutableListOf<String>()

    override suspend fun execute(param: MutableList<String>) {
        tags = param
        sharedPreferencesRepository.setTagReport(param)
    }

    override suspend fun Unit.transformToUseCaseResult(): TagDataResult =
        TagDataResult.Success(tags)
}

class GetTagUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val sharedPreferencesRepository: PreferencesDataManagerRepository
) : BaseUseCase<Nothing?, MutableList<String>, TagDataResult>(dispatcherProvider.dispatcherIO()) {

    override suspend fun execute(param: Nothing?): MutableList<String> =
        sharedPreferencesRepository.getTagReport()

    override suspend fun MutableList<String>.transformToUseCaseResult(): TagDataResult =
        TagDataResult.Success(this)
}