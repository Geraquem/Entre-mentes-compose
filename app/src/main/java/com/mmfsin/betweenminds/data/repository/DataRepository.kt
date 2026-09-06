package com.mmfsin.betweenminds.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.betweenminds.data.ddbb.SharedPrefs
import com.mmfsin.betweenminds.data.ddbb.daos.QuestionsDAO
import com.mmfsin.betweenminds.data.ddbb.daos.RangesDAO
import com.mmfsin.betweenminds.data.mappers.toQuestionList
import com.mmfsin.betweenminds.data.mappers.toRangeList
import com.mmfsin.betweenminds.data.models.QuestionDTO
import com.mmfsin.betweenminds.data.models.RangeDTO
import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range
import com.mmfsin.betweenminds.utils.QUESTIONS
import com.mmfsin.betweenminds.utils.RANGES
import com.mmfsin.betweenminds.utils.VERSION
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.coroutines.resume

class DataRepository @Inject constructor(
    val prefs: SharedPrefs,
    val questionsDAO: QuestionsDAO,
    val rangesDAO: RangesDAO,
) : IDataRepository {

    override suspend fun checkVersion() {
        getVersionFromFirebase(prefs.getVersionSaved())
    }

    private suspend fun getVersionFromFirebase(savedVersion: Long) {
        val fetchBlock: suspend () -> Unit = {
            suspendCancellableCoroutine { coroutine ->
                Firebase.database.reference.get().addOnSuccessListener {
                    val version = it.child(VERSION).value as Long
                    if (version != savedVersion) {
                        prefs.updateVersionSaved(version)
                        prefs.restartValues()
                    }
                    coroutine.resume(Unit)
                }
            }
        }

        try {
            if (savedVersion == -1L) fetchBlock()
            else {
                withTimeout(5000) {
                    fetchBlock()
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("**** FirebaseTimeout **** -> Se agotó el tiempo de espera")
        } catch (e: Exception) {
            println("FirebaseError -> Error al obtener datos: ${e.message}")
        }
    }

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
        if (prefs.getRangesFromServer()) {
            val snapshot = FirebaseDatabase
                .getInstance()
                .getReference(RANGES)
                .get()
                .await()

            val firebaseRanges = snapshot.children
                .mapNotNull { it.getValue(RangeDTO::class.java) }

            if (firebaseRanges.isNotEmpty()) {
                prefs.updateRangesFromServer(false)
                rangesDAO.insertRanges(firebaseRanges)
            }
            return firebaseRanges.toRangeList()

        } else {
            return rangesDAO.getRanges().toRangeList()
        }
    }
}