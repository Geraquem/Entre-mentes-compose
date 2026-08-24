package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IQuestionsOnlineRepository
import javax.inject.Inject

class GetOQuestionsAndNamesUseCase @Inject constructor(
    private val repository: IQuestionsOnlineRepository
) {
    suspend fun execute(roomId: String) = repository.getQuestionsAndNames(roomId)
}