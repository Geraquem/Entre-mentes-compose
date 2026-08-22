package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IQuestionsOnlineRepository
import javax.inject.Inject

class SendOpinionOQuestionsToRoomUseCase @Inject constructor(private val repository: IQuestionsOnlineRepository) {
    suspend fun execute(roomId: String, isCreator: Boolean, round: Int, orangeOpinion: Float) =
        repository.sendOpinionOQuestionsToRoomUseCase(roomId, isCreator, round, orangeOpinion)
}