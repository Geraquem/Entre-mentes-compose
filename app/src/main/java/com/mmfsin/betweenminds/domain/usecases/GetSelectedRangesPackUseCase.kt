package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedRangesPackUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    operator fun invoke(): Flow<Int> = packsRepository.getSelectedRPackId()
}