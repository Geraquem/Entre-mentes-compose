package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.models.Range
import javax.inject.Inject

class GetRangesUseCase @Inject constructor(
    //    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(): List<Range> {
        //        val selectedPack = packRepository.getSelectedQPackId()
        val selectedPack = 0
        val ranges = dataRepository.getRanges()
        return ranges.filter { it.pack == selectedPack }//.shuffled(Random(System.nanoTime()))
    }
}