package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenminds.domain.usecases.GetOQuestionsAndNamesUseCase
import com.mmfsin.betweenminds.domain.usecases.SendOpinionOQuestionsToRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitOtherPlayerOpinionOQuestionsUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.questions.helper.calculatePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class QuestionsOnlineJoinViewModel @Inject constructor(
    private val getOQuestionsAndNamesUseCase: GetOQuestionsAndNamesUseCase,
    private val sendOpinionOQuestionsToRoomUseCase: SendOpinionOQuestionsToRoomUseCase,
    private val waitOtherPlayerOpinionOQuestionsUseCase: WaitOtherPlayerOpinionOQuestionsUseCase,
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
            { sww() },
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

    fun readyMyOpinion() {
        showWaitingOtherPlayerDialog(true)

        _uiState.update {
            it.copy(
                buttonEnabled = false,
                controllerEnabled = false
            )
        }

        val states = uiState.value
        executeUseCase(
            {
                sendOpinionOQuestionsToRoomUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = false,
                    round = states.roundCount,
                    orangeOpinion = states.redSlider.roundToInt(),
                )
            },
            { waitForOtherPlayerOpinion() },
            { sww() })
    }

    private fun waitForOtherPlayerOpinion() {
        val states = uiState.value
        executeUseCase(
            {
                waitOtherPlayerOpinionOQuestionsUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = false,
                    round = states.roundCount
                )
            },
            { otherPlayerOpinion ->
                showWaitingOtherPlayerDialog(false)
                showOtherPlayerOpinion(otherPlayerOpinion)
            },
            { sww() }
        )
    }

    private fun showOtherPlayerOpinion(otherPlayerOpinion: Int) {
        val firstOpBlue = 100 - otherPlayerOpinion
        handleHandsUp(percent = firstOpBlue)

        val states = uiState.value
        val roundPoints = calculatePoints(states.secondOpinionBlue, firstOpBlue)

        _uiState.update {
            it.copy(
                whiteSlider = otherPlayerOpinion.toFloat(),
                firstOpinionBlue = firstOpBlue,
                firstOpinionOrange = otherPlayerOpinion,
                phase = if (states.roundCount != 3) NEXT_ROUND else RESULTS,
                questionPos = states.questionPos + 1,
                roundCount = states.roundCount + 1
            )
        }

        viewModelScope.launch {
            delay(1000)

            _uiState.update {
                it.copy(
                    points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                    confettiTrigger = if (roundPoints > 9) states.confettiTrigger + 1 else 0,
                    showFirstOpinionPercents = true,
                    showWhiteIndicator = true,
                    buttonEnabled = true,
                    buttonText = if (states.roundCount != 3) R.string.btn_next_round else R.string.btn_see_result
                )
            }
        }
    }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}