package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.OnlineRangeRoundData
import com.mmfsin.betweenminds.domain.models.OnlineRangesData

interface IRangesOnlineRepository {
    suspend fun sendMyORangesDataToRoom(onlineData: OnlineRangesData)
    suspend fun waitOtherPlayerORanges(roomId: String, isCreator: Boolean): List<OnlineRangeRoundData>
    suspend fun sendPoints(roomId: String, isCreator: Boolean, points: Int)
    suspend fun waitOtherPlayerPoints(roomId: String, isCreator: Boolean): Int
    suspend fun waitCreatorToRestartGame(roomId: String)
}