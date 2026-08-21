package com.mmfsin.betweenminds.presentation.choose

import com.mmfsin.betweenminds.domain.models.GameType

data class ChooseStates(
    val isLoading: Boolean = false,

    val showSwwDialog: Boolean = false,
    val showErrorJoinedDialog: Boolean = false,

    val gameTypeId: String = "",
    val gameType: GameType? = null,

    val roomCodeToJoin: String = "",
    val roomCodeCreated: String = "",

    val createOnlineRoom: Boolean = false,
    val startQuestionsOffline: Boolean = false,
    val startRangesOffline: Boolean = false
)
