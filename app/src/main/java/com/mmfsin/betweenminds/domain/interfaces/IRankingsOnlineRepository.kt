package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.OnlineRankingData
import com.mmfsin.betweenminds.domain.models.OnlineRankingRoundData

interface IRankingsOnlineRepository {
    suspend fun sendMyORankingsDataToRoom(onlineData: OnlineRankingData)
    suspend fun waitOtherPlayerORankings(roomId: String, isCreator: Boolean): List<OnlineRankingRoundData>
    suspend fun sendPoints(roomId: String, isCreator: Boolean, points: Int)
    suspend fun waitOtherPlayerPoints(roomId: String, isCreator: Boolean): Int
    suspend fun waitCreatorToRestartGame(roomId: String)
}