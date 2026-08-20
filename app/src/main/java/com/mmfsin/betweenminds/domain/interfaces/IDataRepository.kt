package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range

interface IDataRepository {
    suspend fun getQuestions(): List<Question>
    suspend fun getRanges(): List<Range>
}