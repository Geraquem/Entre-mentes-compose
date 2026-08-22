package com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenminds.domain.usecases.SendOpinionOQuestionsToRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.SetOQuestionsInRoomUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineCreatorViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val setOQuestionsInRoomUseCase: SetOQuestionsInRoomUseCase,
    private val sendOpinionOQuestionsToRoomUseCase: SendOpinionOQuestionsToRoomUseCase,
) : BaseViewModel<QuestionsOnlineCreatorStates>(QuestionsOnlineCreatorStates()) {

    init {
        getQuestions()
    }

    fun updateRoomCode(code: String?) {
        if (code == null) sww()
        else _uiState.update { it.copy(roomCode = code) }
    }

    private fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase() },
            { questions ->
                _uiState.update {
                    it.copy(questions = questions)
                }
                setQuestion()
            },
            { sww() }
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

            _uiState.update { it.copy(actualQuestion = newQuestion) }
            setDataInRoom()
        }
    }

    private fun setDataInRoom() {
        val states = uiState.value
        executeUseCase(
            {
                setOQuestionsInRoomUseCase.execute(
                    roomId = states.roomCode,
                    names = Pair(states.blueName, states.orangeName),
                    questions = getQuestionsToRoom(),
                    gameNumber = states.gameNumber
                )
            },
            { _uiState.update { it.copy(isLoading = false) } },
            { sww() }
        )
    }

    private fun getQuestionsToRoom(): List<Question> {
        val states = uiState.value
        if (states.questionPos >= states.questions.size) sww()

        val result = states.questions.subList(
            states.questionPos, (states.questionPos + 4).coerceAtMost(states.questions.size)
        )

        _uiState.update { it.copy(questionPos = states.questionPos + 4) }
        return result
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

    private fun startMyOpinion() {
        _uiState.update {
            it.copy(
                phase = FIRST_OPINION,
                showWhiteIndicator = true,
                showFirstOpinionPercents = true,
                buttonText = R.string.btn_ready,
                controllerEnabled = true,
                buttonEnabled = true,
            )
        }
        openCurtains()
    }

    fun readyMyOpinion() {
        showWaitingOtherPlayerDialog(true)

        val states = uiState.value
        executeUseCase(
            {
                sendOpinionOQuestionsToRoomUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = true,
                    round = states.roundCount,
                    orangeOpinion = states.whiteSlider,
                )
            },
            { waitForOtherPlayerOpinion() },
            { sww() })
    }

    private fun waitForOtherPlayerOpinion() {
        executeUseCase(
            {},
            {},
            { sww() }
        )
    }

    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}