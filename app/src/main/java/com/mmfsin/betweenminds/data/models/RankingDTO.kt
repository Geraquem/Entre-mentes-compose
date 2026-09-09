package com.mmfsin.betweenminds.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.betweenminds.utils.TABLE_RANKING
import java.util.UUID

@Entity(tableName = TABLE_RANKING)
data class RankingDTO(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var pack: Int = 0,
    var text: String = "",
    var option1: String = "",
    var option2: String = "",
    var option3: String = "",
    var option4: String = "",
)
