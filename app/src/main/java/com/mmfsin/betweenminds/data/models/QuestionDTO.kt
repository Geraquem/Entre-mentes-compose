package com.mmfsin.betweenminds.data.models

import androidx.room.PrimaryKey
import java.util.UUID

//@Entity(tableName = TABLE_EVENTS)
data class QuestionDTO(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var question: String = "",
    var pack: Int = 0
)
