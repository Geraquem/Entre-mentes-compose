package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.Pack
import javax.inject.Inject

class GetSinglePackUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    suspend operator fun invoke(
        gameType: GameType,
        packNumber: Int
    ): Pack? = packsRepository.getSelectedPackByType(gameType, packNumber)
}