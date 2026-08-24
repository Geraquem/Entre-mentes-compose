package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import com.mmfsin.betweenminds.domain.usecases.GetOQuestionsAndNamesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineJoinViewModel @Inject constructor(
    private val getOQuestionsAndNamesUseCase: GetOQuestionsAndNamesUseCase,
) : BaseViewModel<QuestionsOnlineJoinStates>(QuestionsOnlineJoinStates()) {

    init {}

    fun updateRoomCode(code: String?) {
        if (code == null) sww()
        else _uiState.update { it.copy(roomCode = code) }
    }

    private fun getQuestionsAndNames() {
        val states = uiState.value
        executeUseCase(
            { getOQuestionsAndNamesUseCase.execute(states.roomCode) },
            { data ->
                _uiState.update { it.copy(showWaitingOtherPlayerDialog = false) }
            },
            {
                sww()
            },
        )
    }

    fun hideInitialDialog() {
        _uiState.update {
            it.copy(
                showInitialDialog = false,
                showWaitingOtherPlayerDialog = true
            )
        }
        getQuestionsAndNames()
        //        viewModelScope.launch {
        //            delay(1000)
        //            _uiState.update { it.copy(showRoundView = false) }
        //            delay(1000)
        //                        startMyOpinion()
        //        }
    }

    fun updateMyOpinionPercents(value: Int) {
        val firstOpBlue = 100 - value
        handleHandsUp(percent = firstOpBlue)

        _uiState.update {
            it.copy(
                whiteSlider = value.toFloat(),
                firstOpinionBlue = firstOpBlue,
                firstOpinionOrange = value
            )
        }
    }

    private fun handleHandsUp(percent: Int) {
        if (percent > 50) {
            _uiState.update { it.copy(blueHandsUp = true, orangeHandsUp = false) }
        } else if (percent == 50) {
            _uiState.update { it.copy(blueHandsUp = false, orangeHandsUp = false) }
        } else {
            _uiState.update { it.copy(blueHandsUp = false, orangeHandsUp = true) }
        }
    }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}