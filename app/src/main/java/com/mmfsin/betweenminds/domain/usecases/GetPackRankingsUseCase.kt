package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.models.Ranking
import javax.inject.Inject

class GetPackRankingsUseCase @Inject constructor(
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(packNumber: Int): List<Ranking> {
        val ranges = dataRepository.getRankings()
        return ranges.filter { it.pack == packNumber }
    }
}