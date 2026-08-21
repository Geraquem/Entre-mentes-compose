package com.mmfsin.betweenminds.presentation.choose.roomcode

import com.mmfsin.betweenminds.domain.models.GameType

data class RoomCodeStates(
    val isLoading: Boolean = true,

    val gameTypeId: String = "",
    val gameType: GameType? = null,

    val roomCode: String = "",

    val goToQuestionsCreator: Boolean = false,
    val goToRangesOnline: Boolean = false,

    val showSwwDialog: Boolean = false,
)