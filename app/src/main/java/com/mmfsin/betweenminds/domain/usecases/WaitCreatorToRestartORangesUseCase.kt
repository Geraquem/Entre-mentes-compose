package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRangesOnlineRepository
import javax.inject.Inject

class WaitCreatorToRestartORangesUseCase @Inject constructor(private val repository: IRangesOnlineRepository) {
    suspend fun execute(roomId: String) = repository.waitCreatorToRestartGame(roomId)
}