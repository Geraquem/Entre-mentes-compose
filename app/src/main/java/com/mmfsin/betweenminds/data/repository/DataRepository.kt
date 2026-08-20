package com.mmfsin.betweenminds.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.mmfsin.betweenminds.data.ddbb.SharedPrefs
import com.mmfsin.betweenminds.data.ddbb.daos.QuestionsDAO
import com.mmfsin.betweenminds.data.mappers.toQuestionList
import com.mmfsin.betweenminds.data.models.QuestionDTO
import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range
import com.mmfsin.betweenminds.utils.QUESTIONS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataRepository @Inject constructor(
    val prefs: SharedPrefs,
    val questionsDAO: QuestionsDAO
) : IDataRepository {

    override suspend fun getQuestions(): List<Question> {
        if (prefs.getQuestionsFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(QUESTIONS)
                .get()
                .await()

            val firebaseQuestions = snapshot.children
                .mapNotNull { it.getValue(QuestionDTO::class.java) }

            if (firebaseQuestions.isNotEmpty()) {
                prefs.updateQuestionsFromServer(false)
                questionsDAO.insertQuestions(firebaseQuestions)
            }
            return firebaseQuestions.toQuestionList()

        } else {
            return questionsDAO.getQuestions().toQuestionList()
        }
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