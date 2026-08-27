package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRangesOnlineRepository
import com.mmfsin.betweenminds.domain.models.OnlineRangesData
import javax.inject.Inject

class SendMyORangesDataToRoomUseCase @Inject constructor(private val repository: IRangesOnlineRepository) {
    suspend fun execute(onlineData: OnlineRangesData) = repository.sendMyORangesDataToRoom(onlineData)
}