package com.mmfsin.betweenminds.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mmfsin.betweenminds.data.billing.BillingManager
import com.mmfsin.betweenminds.data.ddbb.DataStorePrefs
import com.mmfsin.betweenminds.data.ddbb.SharedPrefs
import com.mmfsin.betweenminds.data.ddbb.daos.PacksDAO
import com.mmfsin.betweenminds.data.mappers.getQuestionsPacks
import com.mmfsin.betweenminds.data.mappers.getRangesPacks
import com.mmfsin.betweenminds.data.mappers.getRankingsPacks
import com.mmfsin.betweenminds.data.mappers.toPack
import com.mmfsin.betweenminds.data.models.PackDTO
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Packs
import com.mmfsin.betweenminds.utils.PACKS
import com.mmfsin.betweenminds.utils.QUESTIONS
import com.mmfsin.betweenminds.utils.RANGES
import com.mmfsin.betweenminds.utils.RANKINGS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class PacksRepository @Inject constructor(
    val prefs: SharedPrefs,
    val dataStore: DataStorePrefs,
    val packsDAO: PacksDAO,
    val billingManager: BillingManager
) : IPacksRepository {

    private suspend fun getPacks(): List<PackDTO> {
        return if (prefs.getPacksFromServer()) {
            val snapshot = Firebase.firestore
                .collection(PACKS)
                .get()
                .await()

            val packs = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(PackDTO::class.java)
                } catch (e: Exception) {
                    Log.e("error", "Error parsing pack", e)
                    null
                }
            }

            if (packs.isNotEmpty()) {
                prefs.updatePacksFromServer(false)
                packsDAO.insertPacks(packs)
            }
            packs

        } else packsDAO.getPacks()
    }

    override suspend fun getSelectedPackByType(gameType: GameType, packNumber: Int): Pack? {
        val packs = getPacks()
        val result = when (gameType) {
            GameType.QUESTIONS -> packs.find { it.packType == QUESTIONS && it.packNumber.toInt() == packNumber }
            GameType.RANGES -> packs.find { it.packType == RANGES && it.packNumber.toInt() == packNumber }
            GameType.RANKING -> packs.find { it.packType == RANKINGS && it.packNumber.toInt() == packNumber }
        }
        return result?.toPack()
    }

    override suspend fun getPackById(packId: String): Pack? = packsDAO.getPackById(packId)?.toPack()

    override suspend fun getAllPacks(): Packs {
        val packs = getPacks()
        val questions = packs.filter { it.packType == QUESTIONS }.getQuestionsPacks()
        val ranges = packs.filter { it.packType == RANGES }.getRangesPacks()
        val rankings = packs.filter { it.packType == RANKINGS }.getRankingsPacks()

        return Packs(
            questionsPacks = questions.sortedBy { it.pack.packNumber },
            rangesPacks = ranges.sortedBy { it.pack.packNumber },
            rankingsPacks = rankings.sortedBy { it.pack.packNumber },
        )
    }

    override fun getSelectedQPackId(): Flow<Int> {
        return dataStore.getSelectedQuestionsPack()
    }

    override suspend fun updateSelectedQPackId(packNumber: Int) {
        dataStore.updateSelectedQuestionsPack(packNumber)
    }

    override fun getSelectedRPackId(): Flow<Int> {
        return dataStore.getSelectedRangesPack()
    }

    override suspend fun updateSelectedRPackId(packNumber: Int) {
        dataStore.updateSelectedRangesPack(packNumber)
    }

    override fun getSelectedRankingsPackId(): Flow<Int> {
        return dataStore.getSelectedRankingsPack()
    }

    override suspend fun updateSelectedRankingsPackId(packNumber: Int) {
        dataStore.updateSelectedRankingsPack(packNumber)
    }

    override fun setFreePacks() = prefs.updatePacksPurchased(true)

    override suspend fun checkIfPurchasedPacks(): Pair<Boolean, String?> {
        return if (prefs.arePacksPurchased()) Pair(true, null)
        else {
            val result = billingManager.getAllPacksInfo()
            if (result.first) prefs.updatePacksPurchased(true)
            result
        }
    }

    override suspend fun updatedPacksPurchased() = prefs.updatePacksPurchased(true)


    /****************************************************************************************************/
    /****************************************************************************************************/
    /*********************************  INSERT IN BBDD  *************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    suspend fun insertDataInFirestore() {
        val db = Firebase.firestore
        val batch = db.batch()

        val listToInsert = listToInsert()

        val latch = CountDownLatch(1)

        val usersCollection = db.collection(PACKS)

        for (data in listToInsert) {
            val newDocRef = data["packId"]?.let { usersCollection.document(it.toString()) }
            if (newDocRef != null) {
                batch.set(newDocRef, data)
            }
        }

        batch.commit()
            .addOnSuccessListener {
                latch.countDown()
            }
            .addOnFailureListener {
                latch.countDown()
            }
        withContext(Dispatchers.IO) { latch.await() }
    }


    private fun listToInsert(): List<HashMap<String, Any>> {
        return listOf(
            hashMapOf(
                "packId" to "pack_questions_free",
                "packNumber" to 0,
                "packType" to "questions",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Fhappy.png?alt=media&token=c9e91343-8be3-49bc-b6f3-693b68d83fbc",
                "title" to "Paquete básico",
                "description" to "Perfecto para demostrar cuánto conoces a tus amigos, familiares y personas cercanas.",
            ),
            hashMapOf(
                "packId" to "pack_questions_love",
                "packNumber" to 1,
                "packType" to "questions",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Fheart.png?alt=media&token=e6104f02-b24a-4bc2-b6a2-992fdcb5ada5",
                "title" to "Sobre amor",
                "description" to "Enamoramiento, celos, romanticismo y situaciones íntimas para ver cuánto os conocéis en los sentimental. No vale discutir.",
            ),
            hashMapOf(
                "packId" to "pack_questions_more_1",
                "packNumber" to 2,
                "packType" to "questions",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Falien.png?alt=media&token=1702521d-45b7-4fe3-85c8-088f16ae47fe",
                "title" to "Más preguntas",
                "description" to "¿Quieres más? Con este pack vas a ver de verdad cómo son tus amigos. Ideal para reírte, sorprenderte y conocerlos mejor que nunca.",
            ),
            hashMapOf(
                "packId" to "pack_questions_more_2",
                "packNumber" to 2,
                "packType" to "questions",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Fstar.png?alt=media&token=1be5260e-7b5a-415c-a1d6-83e42a1f2142",
                "title" to "Todavía más preguntas",
                "description" to "Si pensabas que ya os habíais exprimido al máximo, aquí hay otras 50 preguntas diferentes para que sigáis dándole al coco y descubriendo cómo de diferente pensáis sobre vosotros mismos.",
            ),
            hashMapOf(
                "packId" to "pack_ranges_free",
                "packNumber" to 0,
                "packType" to "ranges",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Fone-finger.png?alt=media&token=8051782b-de69-4aa5-bb40-79fc5d3658f1",
                "title" to "Paquete básico",
                "description" to "Pack básico de rangos para que ponerte a prueba con tus compañer@s.",
            ),
            hashMapOf(
                "packId" to "pack_ranges_1",
                "packNumber" to 1,
                "packType" to "ranges",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Ftwo-fingers.png?alt=media&token=b3fda56e-fe31-45e6-b240-318183416903",
                "title" to "Tostadora 3000",
                "description" to "Aquí lo importante no es cómo piensas, si no cómo creen que piensas.",
            ),
            hashMapOf(
                "packId" to "pack_ranges_2",
                "packNumber" to 2,
                "packType" to "ranges",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Fthree-fingers.png?alt=media&token=edff5f03-210e-4b39-94a3-1f53e247e454",
                "title" to "Don Trapito",
                "description" to "No le des muchas vueltas, a veces es más simple de lo que parece.",
            ),
            hashMapOf(
                "packId" to "pack_ranges_3",
                "packNumber" to 3,
                "packType" to "ranges",
                "icon" to "https://firebasestorage.googleapis.com/v0/b/entre-mentes.firebasestorage.app/o/Packs%2Ffour-fingers.png?alt=media&token=139dd119-0215-444a-846b-5ca3b2bd5c39",
                "title" to "Relojito tardón",
                "description" to "Tú sigue intentandolo que seguro que algo aciertas.",
            ),
        )
    }
}