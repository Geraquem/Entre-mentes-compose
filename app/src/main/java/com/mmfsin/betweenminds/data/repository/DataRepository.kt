package com.mmfsin.betweenminds.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.betweenminds.data.mappers.toQuestionList
import com.mmfsin.betweenminds.data.models.QuestionDTO
import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range
import com.mmfsin.betweenminds.utils.QUESTIONS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataRepository @Inject constructor() : IDataRepository {

    override suspend fun getQuestions(): List<Question> {
        val snapshot = FirebaseDatabase
            .getInstance()
            .getReference(QUESTIONS)
            .get()
            .await()

        return snapshot.children
            .mapNotNull { it.getValue(QuestionDTO::class.java) }
            .toQuestionList()
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