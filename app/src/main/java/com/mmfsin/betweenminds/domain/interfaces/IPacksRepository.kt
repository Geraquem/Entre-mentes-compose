package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Packs
import com.mmfsin.betweenminds.domain.models.QuestionsPack
import com.mmfsin.betweenminds.domain.models.RangesPack
import kotlinx.coroutines.flow.Flow

interface IPacksRepository {
    suspend fun getPackById(packId: String): Pack?

    suspend fun getDataSelectedPack(gameType: String): Pair<String?, String?>

    suspend fun getSeparatedPacks(): Packs

    fun getSelectedQPackId(): Flow<Int>
    fun editSelectedQPackId(packNumber: Int)

    fun getSelectedRPackId(): Flow<Int>
    fun editSelectedRPackId(packNumber: Int)

    fun setFreePacks()
    fun checkIfPacksAreFree(): Boolean
}