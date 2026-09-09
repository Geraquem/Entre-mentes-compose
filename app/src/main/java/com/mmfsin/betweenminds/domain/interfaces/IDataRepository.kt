package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.Ranking
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range

interface IDataRepository {
    suspend fun checkVersion()

    suspend fun getQuestions(): List<Question>
    suspend fun getRanges(): List<Range>
    suspend fun getRanking(): List<Ranking>
}