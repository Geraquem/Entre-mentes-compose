package com.mmfsin.betweenminds.presentation.choose

import com.mmfsin.betweenminds.domain.models.GameType

data class ChooseStates(
    val gameTypeId: String = "",
    val gameType: GameType? = null,

    val roomCodeToJoin: String = "",
    val roomCodeCreated: String = "",

    val createRoomQuestionsOnline: Boolean = false,
    val startQuestionsOffline: Boolean = false,

    val createRoomRangesOnline: Boolean = false,
    val startRangesOffline: Boolean = false
)
