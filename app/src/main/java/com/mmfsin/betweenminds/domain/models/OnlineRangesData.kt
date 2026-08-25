package com.mmfsin.betweenminds.domain.models

data class OnlineRangesData(
    val roomId: String,
    val isCreator: Boolean,
    val data: List<OnlineRangeRoundData>
)

data class OnlineRangeRoundData(
    val round: Int,
    val bullseyePosition: Float,
    val hint: String,
    val leftRange: String,
    val rightRange: String,
)
