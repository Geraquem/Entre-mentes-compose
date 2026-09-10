package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline

import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Ranking
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.domain.models.RankingPhaseType
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_FIRST
import com.mmfsin.betweenminds.domain.models.emptyRankingBoxList
import java.util.Collections.emptyList

data class RankingOfflineStates(
    val isLoading: Boolean = true,

    val showInitialDialog: Boolean = true,
    val showDialogBetweenPhases: Boolean = false,
    val showExitDialog: Boolean = false,
    val showSwwDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,
    val phase: RankingPhaseType = ORDER_FIRST,

    val rankings: List<Ranking> = emptyList(),
    var rankingPos: Int = 0,
    val actualRankingText: String = "",
    val actualRankings: MutableList<String> = mutableListOf("", "", "", ""),
    val actualRankingsAux: MutableList<String> = mutableListOf("", "", "", ""),

    val rankingBoxList: MutableList<RankingBox> = emptyRankingBoxList(),

    val firstSortedList: MutableList<RankingBox> =  emptyRankingBoxList(),
    val secondSortedList: MutableList<RankingBox> = emptyRankingBoxList(),
    val showComparativeList: Boolean = false,

    val points: List<Int?> = listOf(null, null, null, null),
    val confettiTrigger: Int = 0,
    val shakeTrigger: Boolean = false,
    val shakeVerticalTrigger: Boolean = false,

    val dragEnabled: Boolean = true,
    val buttonEnabled: Boolean = true,
    val buttonText: Int = R.string.btn_ready
)
