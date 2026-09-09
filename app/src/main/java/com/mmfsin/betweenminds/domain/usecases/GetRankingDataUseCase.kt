package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Ranking
import javax.inject.Inject

class GetRankingDataUseCase @Inject constructor(
    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(): List<Ranking> {
        val selectedPack = packRepository.getSelectedQPackId()
        val rankings = dataRepository.getRanking()
        return rankings/*.filter { it.pack == selectedPack.first() }.shuffled(Random(System.nanoTime()))*/
    }
}