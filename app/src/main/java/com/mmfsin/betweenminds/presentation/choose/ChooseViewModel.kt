package com.mmfsin.betweenminds.presentation.choose

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenminds.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.usecases.CreateRoomUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRoomUseCase: CreateRoomUseCase
) : BaseViewModel<ChooseStates>(ChooseStates()) {

    private val gameTypeId: String? = savedStateHandle["gameTypeId"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
        if (gameTypeId == null) sww()
        else {
            val type = getGameTypeById(gameTypeId)
            _uiState.update {
                it.copy(
                    gameTypeId = gameTypeId,
                    gameType = type
                )
            }
        }
    }

    fun onRoomCodeChanged(value: String) = _uiState.update { it.copy(roomCodeToJoin = value) }

    fun createRoom() {
        val states = uiState.value
        executeUseCase(
            { createRoomUseCase.execute(states.gameTypeId) },
            { code ->
                if (code == null) sww()
                else {
                    _uiState.update { it.copy(roomCodeCreated = code) }
                    when (states.gameType) {
                        QUESTIONS -> createRoomQuestionsOnline(true)
                        RANGES -> createRoomRangesOnline(true)
                        else -> sww()
                    }
                }
            },
            {}
        )
    }

    fun joinRoom() {

    }

    fun playOffline() {
        val gameType = uiState.value.gameType
        gameType?.let { type ->
            when (type) {
                QUESTIONS -> startQuestionsOffline(true)
                RANGES -> startRangesOffline(true)
            }
        }
    }

    fun createRoomQuestionsOnline(value: Boolean) = _uiState.update { it.copy(createRoomQuestionsOnline = value) }
    fun startQuestionsOffline(value: Boolean) = _uiState.update { it.copy(startQuestionsOffline = value) }
    fun createRoomRangesOnline(value: Boolean) = _uiState.update { it.copy(createRoomRangesOnline = value) }
    fun startRangesOffline(value: Boolean) = _uiState.update { it.copy(startRangesOffline = value) }

    private fun sww() {}
}