package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline

import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Ranking
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.domain.models.RankingPhaseType
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_FIRST
import java.util.Collections.emptyList

data class RankingOfflineStates(
    val isLoading: Boolean = true,

    val showInitialDialog: Boolean = true,
    val showExitDialog: Boolean = false,
    val showSwwDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,
    val phase: RankingPhaseType = ORDER_FIRST,

    val rankings: List<Ranking> = emptyList(),
    var rankingPos: Int = 0,
    val actualRankingText: String = "",
    val actualRankingTopText: String = "",
    val actualRankingBottomText: String = "",
    val actualRankings: MutableList<String> = mutableListOf("", "", "", ""),
    val actualRankingsAux: MutableList<String> = mutableListOf("", "", "", ""),

    val rankingBoxList: MutableList<RankingBox> = emptyList(),

    val sortedListOne: MutableList<RankingBox> = emptyList(),
    val sortedListSecond: MutableList<RankingBox> = emptyList(),

    val points: List<Int?> = listOf(null, null, null, null),
    val confettiTrigger: Int = 0,
    val shakeTrigger: Boolean = false,

    val buttonEnabled: Boolean = true,
    val buttonText: Int = R.string.btn_ready
)
