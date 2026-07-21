package com.mmfsin.betweenmindscompose.domain.interfaces

import com.mmfsin.betweenmindscompose.domain.models.Question
import com.mmfsin.betweenmindscompose.domain.models.Range

interface IDataRepository {
    suspend fun getQuestions(): List<Question>
    suspend fun getRanges(): List<Range>
}