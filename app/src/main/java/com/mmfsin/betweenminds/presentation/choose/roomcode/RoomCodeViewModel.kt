package com.mmfsin.betweenminds.presentation.choose.roomcode

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenminds.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.usecases.WaitToJoinOtherPlayerUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RoomCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val waitToJoinOtherPlayerUseCase: WaitToJoinOtherPlayerUseCase,
) : BaseViewModel<RoomCodeStates>(RoomCodeStates()) {

    private val roomCode: String? = savedStateHandle["roomCode"]
    private val gameTypeId: String? = savedStateHandle["gameTypeId"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
        if (roomCode == null) sww()
        else {
            _uiState.update { it.copy(roomCode = roomCode) }
            waitForOtherPlayer()
        }
    }

    private fun waitForOtherPlayer() {
        val states = uiState.value
        executeUseCase(
            { waitToJoinOtherPlayerUseCase.execute(states.roomCode) },
            {
                val type = getGameTypeById(states.gameTypeId)
                when (type) {
                    QUESTIONS -> goToQuestionsCreator()
                    RANGES -> goToRangesOnline()
                }
            },
            { sww() }
        )
    }

    private fun goToQuestionsCreator() = _uiState.update { it.copy(goToQuestionsCreator = true) }
    private fun goToRangesOnline() = _uiState.update { it.copy(goToRangesOnline = true) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}