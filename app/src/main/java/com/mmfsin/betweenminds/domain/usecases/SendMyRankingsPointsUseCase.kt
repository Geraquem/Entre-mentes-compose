package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRankingsOnlineRepository
import javax.inject.Inject

class SendMyRankingsPointsUseCase @Inject constructor(private val repository: IRankingsOnlineRepository) {
    suspend fun execute(roomId: String, isCreator: Boolean, points: Int) =
        repository.sendPoints(roomId, isCreator, points)
}