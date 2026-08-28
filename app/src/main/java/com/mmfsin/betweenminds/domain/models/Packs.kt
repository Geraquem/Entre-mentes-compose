package com.mmfsin.betweenminds.domain.models

data class Packs(
    val questionsPacks: List<QuestionsPack>,
    val rangesPacks: List<RangesPack>
)

data class QuestionsPack(
    val pack: Pack,
    var questions: List<Question> = emptyList()
)

data class RangesPack(
    val pack: Pack,
    var ranges: List<Range> = emptyList()
)

data class Pack(
    var packId: String = "",
    var packType: String = "",
    var packNumber: Int = 0,
    var packTitle: String,
    var packDescription: String,
    var packPrice: String,
    var packIcon: String,
    var selected: Boolean = false,
    var purchased: Boolean = false,
)