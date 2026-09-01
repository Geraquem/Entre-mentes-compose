package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRangesOnlineRepository
import javax.inject.Inject

class SendMyRangesPointsUseCase @Inject constructor(private val repository: IRangesOnlineRepository) {
    suspend fun execute(roomId: String, isCreator: Boolean, points: Int) =
        repository.sendPoints(roomId, isCreator, points)
}