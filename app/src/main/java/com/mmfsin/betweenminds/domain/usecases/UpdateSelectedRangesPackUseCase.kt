package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import javax.inject.Inject

class UpdateSelectedRangesPackUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    suspend operator fun invoke(newPack: Int) = packsRepository.updateSelectedRPackId(newPack)
}