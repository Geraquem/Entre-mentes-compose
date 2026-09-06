package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Packs
import kotlinx.coroutines.flow.Flow

interface IPacksRepository {
    suspend fun getAllPacks(): Packs

    suspend fun getSelectedPackByType(gameType: GameType, packNumber: Int): Pack?
    suspend fun getPackById(packId: String): Pack?

    fun getSelectedQPackId(): Flow<Int>
    suspend fun updateSelectedQPackId(packNumber: Int)

    fun getSelectedRPackId(): Flow<Int>
    suspend fun updateSelectedRPackId(packNumber: Int)

    fun setFreePacks()
    fun checkIfPacksAreFree(): Boolean

    suspend fun checkIfPurchasedPacks(): Pair<Boolean, String?>
}