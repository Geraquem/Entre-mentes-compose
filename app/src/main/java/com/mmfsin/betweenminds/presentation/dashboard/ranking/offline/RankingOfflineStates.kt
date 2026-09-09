package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline

import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Ranking
import com.mmfsin.betweenminds.domain.models.RankingBox
import java.util.Collections.emptyList

data class RankingOfflineStates(
    val isLoading: Boolean = true,

    val showInitialDialog: Boolean = true,
    val showExitDialog: Boolean = false,
    val showSwwDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,

    val rankings: List<Ranking> = emptyList(),
    var rankingPos: Int = 0,
    val actualRankingText: String = "",
    val actualRankingTopText: String = "",
    val actualRankingBottomText: String = "",
    val actualRankings: MutableList<String> = mutableListOf("", "", "", ""),

    val rankingBoxList: MutableList<RankingBox> = emptyList(),

    val buttonEnabled: Boolean = false,
    val buttonText: Int = R.string.btn_ready
)
