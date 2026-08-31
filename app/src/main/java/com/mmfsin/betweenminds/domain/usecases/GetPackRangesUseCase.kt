package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Range
import javax.inject.Inject

class GetPackRangesUseCase @Inject constructor(
    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(packNumber: Int): List<Range> {
        val ranges = dataRepository.getRanges()
        return ranges.filter { it.pack == packNumber }
    }
}