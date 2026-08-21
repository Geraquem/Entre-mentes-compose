package com.mmfsin.betweenminds.presentation.choose

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenminds.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.usecases.CreateRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.JoinRoomUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRoomUseCase: CreateRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
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
        showLoading(true)

        val states = uiState.value
        executeUseCase(
            { createRoomUseCase.execute(states.gameTypeId) },
            { code ->
                if (code == null) sww()
                else {
                    _uiState.update { it.copy(roomCodeCreated = code) }
                    createOnlineRoom(true)
                    showLoading(false)
                }
            },
            { sww() }
        )
    }

    fun joinRoom() {
        showLoading(true)
        val states = uiState.value
        executeUseCase(
            {
                joinRoomUseCase.execute(
                    roomId = states.roomCodeToJoin,
                    gameType = states.gameTypeId
                )
            },
            { joined ->
                if (joined) {

                } else {
                    joinedError(true)
                    showLoading(false)
                }
            },
            {
                joinedError(true)
                showLoading(false)
            }
        )
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

    fun createOnlineRoom(value: Boolean) = _uiState.update { it.copy(createOnlineRoom = value) }
    fun startQuestionsOffline(value: Boolean) = _uiState.update { it.copy(startQuestionsOffline = value) }
    fun startRangesOffline(value: Boolean) = _uiState.update { it.copy(startRangesOffline = value) }

    fun showLoading(value: Boolean) = _uiState.update { it.copy(isLoading = value) }

    fun joinedError(value: Boolean) = _uiState.update { it.copy(showErrorJoinedDialog = value) }
    fun sww(value: Boolean = true) = _uiState.update { it.copy(showSwwDialog = value) }
}