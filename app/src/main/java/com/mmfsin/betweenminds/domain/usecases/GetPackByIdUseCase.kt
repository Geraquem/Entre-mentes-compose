package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Pack
import javax.inject.Inject

class GetPackByIdUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
) {
    suspend operator fun invoke(packId: String): Pack? = packsRepository.getPackById(packId)
}
