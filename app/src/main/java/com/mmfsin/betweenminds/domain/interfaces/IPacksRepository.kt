package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Packs
import kotlinx.coroutines.flow.Flow

interface IPacksRepository {
    suspend fun getPackById(packId: String): Pack?

    suspend fun getDataSelectedPack(gameType: String): Pair<String?, String?>

    suspend fun getSeparatedPacks(): Packs

    fun getSelectedQPackId(): Flow<Int>
    suspend fun updateSelectedQPackId(packNumber: Int)

    fun getSelectedRPackId(): Flow<Int>
    suspend fun updateSelectedRPackId(packNumber: Int)

    fun setFreePacks()
    fun checkIfPacksAreFree(): Boolean
}