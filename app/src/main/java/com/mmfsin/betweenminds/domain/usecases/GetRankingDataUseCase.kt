package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Ranking
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.random.Random

class GetRankingDataUseCase @Inject constructor(
    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(): List<Ranking> {
        val selectedPack = packRepository.getSelectedRankingsPackId()
        val rankings = dataRepository.getRankings()
        return rankings.filter { it.pack == selectedPack.first() }.shuffled(Random(System.nanoTime()))
    }
}