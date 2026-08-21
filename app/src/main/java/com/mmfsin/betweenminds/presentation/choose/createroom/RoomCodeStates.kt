package com.mmfsin.betweenminds.presentation.choose.createroom

import com.mmfsin.betweenminds.domain.models.GameType

data class RoomCodeStates(
    val isLoading: Boolean = true,

    val gameTypeId: String = "",
    val gameType: GameType? = null,

    val roomCode: String = "",
)