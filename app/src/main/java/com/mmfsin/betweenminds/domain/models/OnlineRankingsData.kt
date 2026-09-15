package com.mmfsin.betweenminds.domain.models

data class OnlineRankingData(
    val roomId: String,
    val isCreator: Boolean,
    val data: List<OnlineRankingRoundData>
)

data class OnlineRankingRoundData(
    val round: Int,
    val text: String,
    val rankingTexts: List<String>,
    val rankingsSorted: List<RankingBox>,
)
