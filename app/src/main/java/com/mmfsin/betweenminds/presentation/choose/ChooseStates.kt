package com.mmfsin.betweenminds.presentation.choose

import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.Pack

data class ChooseStates(
    val isLoading: Boolean = true,

    val showSwwDialog: Boolean = false,
    val showErrorJoinedDialog: Boolean = false,

    val packIcon: String = "",
    val packTitle: String = "",

    val gameTypeId: String = "",
    val gameType: GameType? = null,

    val roomCodeToJoin: String = "",
    val roomCodeCreated: String = "",

    val createOnlineRoom: Boolean = false,

    val joinToQuestionsOnline: Boolean = false,
    val joinToRangesOnline: Boolean = false,

    val startQuestionsOffline: Boolean = false,
    val startRangesOffline: Boolean = false
)
