package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRankingsOnlineRepository
import com.mmfsin.betweenminds.domain.models.OnlineRankingData
import javax.inject.Inject

class SendMyORankingDataToRoomUseCase @Inject constructor(private val repository: IRankingsOnlineRepository) {
    suspend fun execute(onlineData: OnlineRankingData) = repository.sendMyORankingsDataToRoom(onlineData)
}