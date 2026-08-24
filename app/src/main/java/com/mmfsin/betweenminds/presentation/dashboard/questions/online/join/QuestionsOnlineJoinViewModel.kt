package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineJoinViewModel @Inject constructor(
) : BaseViewModel<QuestionsOnlineJoinStates>(QuestionsOnlineJoinStates()) {

    init {
    }

    fun updateRoomCode(code: String?) {
        if (code == null) sww()
        else _uiState.update { it.copy(roomCode = code) }
    }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}