package com.mmfsin.betweenminds.domain.interfaces

import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Packs
import com.mmfsin.betweenminds.domain.models.QuestionsPack
import com.mmfsin.betweenminds.domain.models.RangesPack

interface IPacksRepository {
    suspend fun getPackById(packId: String): Pack?

    suspend fun getDataSelectedPack(gameType: String): Pair<String?, String?>

    suspend fun getSeparatedPacks(): Packs

    fun getSelectedQPackId(): Int
    fun editSelectedQPackId(packNumber: Int)

    fun getSelectedRPackId(): Int
    fun editSelectedRPackId(packNumber: Int)

    fun setFreePacks()
    fun checkIfPacksAreFree(): Boolean
}