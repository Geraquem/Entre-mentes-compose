package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRankingsOnlineRepository
import com.mmfsin.betweenminds.domain.models.OnlineRankingRoundData
import javax.inject.Inject

class WaitOtherPlayerORankingsUseCase @Inject constructor(private val repository: IRankingsOnlineRepository) {
    suspend fun execute(roomId: String, isCreator: Boolean): List<OnlineRankingRoundData> =
        repository.waitOtherPlayerORankings(roomId, isCreator)
}