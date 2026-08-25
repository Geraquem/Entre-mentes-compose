package com.mmfsin.betweenminds.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.betweenminds.data.models.RangeDTO

@Dao
interface RangesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRanges(questions: List<RangeDTO>)

    @Query("SELECT * FROM table_ranges")
    suspend fun getRanges(): List<RangeDTO>
}