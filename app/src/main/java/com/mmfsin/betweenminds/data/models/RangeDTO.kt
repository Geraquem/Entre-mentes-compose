package com.mmfsin.betweenminds.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.betweenminds.utils.TABLE_RANGES
import java.util.UUID

@Entity(tableName = TABLE_RANGES)
data class RangeDTO(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var leftRange: String = "",
    var rightRange: String = "",
    var pack: Int = 0
)
