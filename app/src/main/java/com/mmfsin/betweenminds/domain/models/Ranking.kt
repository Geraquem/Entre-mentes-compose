package com.mmfsin.betweenminds.domain.models

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
    var text: String,
)

enum class RankingPhaseType {
    ORDER_FIRST,
    ORDER_SECOND,
    NEXT_ROUND,
    RESULTS
}