package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.usecases.GetOQuestionsAndNamesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineJoinViewModel @Inject constructor(
    private val getOQuestionsAndNamesUseCase: GetOQuestionsAndNamesUseCase,
) : BaseViewModel<QuestionsOnlineJoinStates>(QuestionsOnlineJoinStates()) {

    fun updateRoomCode(code: String?) {
        if (code == null) sww()
        else {
            _uiState.update { it.copy(roomCode = code) }
            getQuestionsAndNames()
        }
    }

    private fun getQuestionsAndNames() {
        val states = uiState.value
        executeUseCase(
            { getOQuestionsAndNamesUseCase.execute(states.roomCode) },
            { data ->
                _uiState.update {
                    it.copy(
                        blueName = data.blueName,
                        orangeName = data.orangeName,
                        questions = data.questions
                    )
                }
                setQuestion()
            },
            {
                sww()
            },
        )
    }

    private fun setQuestion() {
        val states = uiState.value
        val questions = states.questions

        if (questions.isEmpty()) sww()
        else {
            val newQuestion = if (states.questionPos >= states.questions.size) {
                _uiState.update { it.copy(questionPos = 0) }
                questions[0].question
            } else questions[states.questionPos].question

            _uiState.update {
                it.copy(
                    actualQuestion = newQuestion,
                    isLoading = false
                )
            }
        }
    }

    fun hideInitialDialog() {
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(showRoundView = false) }
            delay(1000)
            startMyOpinion()
        }
    }

    private fun startMyOpinion() {
        _uiState.update {
            it.copy(
                phase = FIRST_OPINION,
                showRedIndicator = true,
                showSecondOpinionPercents = true,
                buttonText = R.string.btn_ready,
                controllerEnabled = true,
                buttonEnabled = true,
            )
        }
        openCurtains()
    }

    fun updateMyOpinionPercents(value: Int) {
        val secondOpBlue = 100 - value
        handleHandsUp(percent = secondOpBlue)

        _uiState.update {
            it.copy(
                redSlider = value.toFloat(),
                secondOpinionBlue = secondOpBlue,
                secondOpinionOrange = value
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