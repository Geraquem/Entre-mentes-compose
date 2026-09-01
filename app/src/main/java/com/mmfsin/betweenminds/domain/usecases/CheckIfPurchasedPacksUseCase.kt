package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import javax.inject.Inject

class CheckIfPurchasedPacksUseCase @Inject constructor(private val repository: IPacksRepository) {
    suspend fun execute(): Boolean = repository.checkIfPurchasedPacks()
}