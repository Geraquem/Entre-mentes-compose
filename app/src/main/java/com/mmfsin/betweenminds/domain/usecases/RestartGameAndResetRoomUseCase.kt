package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRoomRepository
import javax.inject.Inject

class RestartGameAndResetRoomUseCase @Inject constructor(private val repository: IRoomRepository) {
    suspend fun execute(roomId: String) = repository.restartGameAndResetRoom(roomId)
}