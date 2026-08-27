package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRangesOnlineRepository
import com.mmfsin.betweenminds.domain.models.OnlineRangeRoundData
import javax.inject.Inject

class WaitOtherPlayerORangesUseCase @Inject constructor(private val repository: IRangesOnlineRepository) {
    suspend fun execute(roomId: String, isCreator: Boolean): List<OnlineRangeRoundData> =
        repository.waitOtherPlayerORanges(roomId, isCreator)
}