package com.mmfsin.betweenmindscompose.data.repository

import com.mmfsin.betweenmindscompose.domain.interfaces.IDataRepository
import com.mmfsin.betweenmindscompose.domain.models.Question
import com.mmfsin.betweenmindscompose.domain.models.Range
import javax.inject.Inject

class DataRepository @Inject constructor() : IDataRepository {
    override suspend fun getQuestions(): List<Question> {
        val questions = List(4) { i ->
            Question(
                question = "Pregunta $i",
                pack = 0
            )
        }
        return questions
    }

    override suspend fun getRanges(): List<Range> {
        val ranges = List(4) { i ->
            Range(
                leftRange = "LeftRange ${i + 1}",
                rightRange = "RightRange ${i + 1}",
                pack = 0
            )
        }
        return ranges
    }
}