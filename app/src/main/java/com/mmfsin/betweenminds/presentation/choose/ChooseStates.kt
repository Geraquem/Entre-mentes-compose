package com.mmfsin.betweenminds.presentation.choose

import com.mmfsin.betweenminds.domain.models.GameType

data class ChooseStates(
    val gameType: GameType? = null,

    val roomCode: String = "",

    val startQuestionsOnline: Boolean = false,
    val startQuestionsOffline: Boolean = false,
    val startRangesOnline: Boolean = false,
    val startRangesOffline: Boolean = false
)
