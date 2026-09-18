package com.mmfsin.betweenminds.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.betweenminds.data.models.RankingDTO

@Dao
interface RankingsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRankings(rankings: List<RankingDTO>)

    @Query("SELECT * FROM table_ranking")
    suspend fun getRankings(): List<RankingDTO>
}