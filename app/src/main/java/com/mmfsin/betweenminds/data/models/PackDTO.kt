package com.mmfsin.betweenminds.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.betweenminds.utils.TABLE_PACKS
import com.mmfsin.betweenminds.utils.TABLE_RANGES
import java.util.UUID

@Entity(tableName = TABLE_PACKS)
data class PackDTO(
    @PrimaryKey
    var packId: String = "",
    var packNumber: Long = 0,
    var packType: String = "",
    var price: String = "",
    var icon: String = "",
    var title: String = "",
    var description: String = "",
)
