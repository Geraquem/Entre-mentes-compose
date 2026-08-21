package com.mmfsin.betweenminds.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmfsin.betweenminds.domain.interfaces.IRoomRepository
import com.mmfsin.betweenminds.utils.PLAYER_1
import com.mmfsin.betweenminds.utils.ROOMS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class RoomRepository @Inject constructor(
) : IRoomRepository {

    override suspend fun createRoom(gameType: String): String? {
        val db = Firebase.firestore
        val latch = CountDownLatch(1)

        var roomCodeCreated: String? = null

        fun tryCreate(attempt: Int) {
            if (attempt > 5) {
                println("No se ha podido crear la sala después de 5 intentos.")
                return
            }

            val roomId = generateRoomId()
            val roomRef = db.collection(ROOMS).document(roomId)

            db.runTransaction { transaction ->
                val snapshot = transaction.get(roomRef)
                if (snapshot.exists()) {
                    throw Exception("Código $roomId ya está en uso")
                }

                transaction.set(
                    roomRef, hashMapOf(
                        "gameType" to gameType,
                        "roomId" to roomId,
                        "players" to listOf(PLAYER_1),
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                )
            }.addOnSuccessListener {
                roomCodeCreated = roomId
                latch.countDown()
                println("Sala creada con código: $roomId")
            }.addOnFailureListener { e ->
                if (attempt == 5) {
                    latch.countDown()
                    return@addOnFailureListener
                }
                println("Error creando sala: ${e.message}. Reintentando...")
                tryCreate(attempt + 1)
            }
        }

        tryCreate(1)
        withContext(Dispatchers.IO) { latch.await() }
        return roomCodeCreated
    }

    private fun generateRoomId(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..4).map { chars.random() }.joinToString("")
    }

    override suspend fun joinRoom(roomId: String, gameType: String): Boolean {
        return false
    }

    override suspend fun waitToJoinRoom(roomId: String) {
    }

    override suspend fun restartGameAndResetRoom(roomId: String) {
    }
}