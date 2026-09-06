package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import javax.inject.Inject

class UpdatePacksPurchasedUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    suspend operator fun invoke() = packsRepository.updatedPacksPurchased()
}