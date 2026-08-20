package com.mmfsin.betweenminds.data.mappers

import com.mmfsin.betweenminds.data.models.QuestionDTO
import com.mmfsin.betweenminds.domain.models.Question

fun QuestionDTO.toQuestion() = Question(
    question = question,
    pack = pack
)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }