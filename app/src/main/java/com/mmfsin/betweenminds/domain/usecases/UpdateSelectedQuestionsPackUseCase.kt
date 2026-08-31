package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import javax.inject.Inject

class UpdateSelectedQuestionsPackUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    suspend operator fun invoke(newPack: Int) = packsRepository.updateSelectedQPackId(newPack)
}