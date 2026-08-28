package com.mmfsin.betweenminds.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.betweenminds.data.models.PackDTO

@Dao
interface PacksDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPacks(questions: List<PackDTO>)

    @Query("SELECT * FROM table_packs")
    suspend fun getPacks(): List<PackDTO>

    @Query("SELECT * FROM table_packs WHERE packId = :packId")
    suspend fun getPackById(packId: String): PackDTO

    @Query("DELETE FROM table_packs")
    suspend fun deleteAllPacks()
}