package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IRoomRepository
import javax.inject.Inject

class WaitToJoinOtherPlayerUseCase @Inject constructor(private val repository: IRoomRepository) {
    suspend fun execute(roomId: String) = repository.waitToJoinRoom(roomId)
}