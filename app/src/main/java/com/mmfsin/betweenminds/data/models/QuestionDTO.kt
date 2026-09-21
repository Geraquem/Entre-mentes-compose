package com.mmfsin.betweenminds.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.betweenminds.utils.TABLE_QUESTIONS
import java.util.UUID

@Keep
@Entity(tableName = TABLE_QUESTIONS)
data class QuestionDTO(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var question: String = "",
    var pack: Int = 0
)
