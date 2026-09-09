package com.mmfsin.betweenminds.data.mappers

import com.mmfsin.betweenminds.data.models.PackDTO
import com.mmfsin.betweenminds.data.models.QuestionDTO
import com.mmfsin.betweenminds.data.models.RangeDTO
import com.mmfsin.betweenminds.data.models.RankingDTO
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.QuestionsPack
import com.mmfsin.betweenminds.domain.models.Range
import com.mmfsin.betweenminds.domain.models.RangesPack
import com.mmfsin.betweenminds.domain.models.Ranking

fun QuestionDTO.toQuestion() = Question(
    question = question,
    pack = pack
)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }

fun RangeDTO.toRange() = Range(
    leftRange = leftRange,
    rightRange = rightRange,
    pack = pack
)

fun List<RangeDTO>.toRangeList() = this.map { it.toRange() }

fun RankingDTO.toRanking() = Ranking(
    id = id,
    pack = pack,
    text = text,
    topText = topText,
    bottomText = bottomText,
    rankings = listOf(option1, option2, option3, option4),
)

fun List<RankingDTO>.toRankingList() = this.map { it.toRanking() }

fun PackDTO.toPack() = Pack(
    packId = packId,
    packType = packType,
    packNumber = packNumber.toInt(),
    packTitle = title,
    packDescription = description,
    packPrice = price,
    packIcon = icon
)

fun PackDTO.toQuestionPack() = QuestionsPack(pack = this.toPack())
fun List<PackDTO>.getQuestionsPacks() = this.map { it.toQuestionPack() }

fun PackDTO.toRangesPack() = RangesPack(pack = this.toPack())
fun List<PackDTO>.getRangesPacks() = this.map { it.toRangesPack() }