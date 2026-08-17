package com.mmfsin.betweenmindscompose.data.repository

import com.mmfsin.betweenmindscompose.domain.interfaces.IDataRepository
import com.mmfsin.betweenmindscompose.domain.models.Question
import com.mmfsin.betweenmindscompose.domain.models.Range
import javax.inject.Inject

class DataRepository @Inject constructor() : IDataRepository {
    override suspend fun getQuestions(): List<Question> {
        return listOf(
            Question(
                question = "Pregunta 1",
                pack = 0
            ),
            Question(
                question = "Pregunta 2",
                pack = 0
            ),
            Question(
                question = "Pregunta 3",
                pack = 0
            ),
            Question(
                question = "Pregunta 4",
                pack = 0
            ),
            Question(
                question = "Pregunta 5",
                pack = 0
            ),
            Question(
                question = "Pregunta 6",
                pack = 0
            ),
            Question(
                question = "Pregunta 7",
                pack = 0
            ),
            Question(
                question = "Pregunta 8",
                pack = 0
            ),
            Question(
                question = "Pregunta 9",
                pack = 0
            ),
        )
    }

    override suspend fun getRanges(): List<Range> {
        return emptyList()
    }
}