package com.mmfsin.betweenminds.domain.models

import androidx.compose.runtime.mutableStateListOf

data class Ranking(
    var id: String,
    var pack: Int,
    var text: String,
    var topText: String,
    var bottomText: String,
    var rankings: List<String>
)

data class RankingBox(
    val id: Int,
    var text: String
)

fun emptyRankingBoxList(): MutableList<RankingBox> = mutableStateListOf(
    RankingBox(0, ""),
    RankingBox(1, ""),
    RankingBox(2, ""),
    RankingBox(3, ""),
)

enum class RankingPhaseType {
    ORDER_FIRST,
    ORDER_SECOND,
    NEXT_ROUND,
    RESULTS
}