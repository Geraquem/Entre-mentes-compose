package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.NEXT_ROUND
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.SECOND_OPINION
import com.mmfsin.betweenmindscompose.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.calculatePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsOfflineViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : BaseViewModel<QuestionsOfflineStates>(QuestionsOfflineStates()) {

    init {
        getQuestions()
    }

    private fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase() },
            { questions ->
                _uiState.update { it.copy(questions = questions) }
                setQuestion()
            },
            {}
        )
    }

    fun hideInitialDialog() {
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(showRoundView = false) }

            delay(1000)
            startOpinions()
        }
    }

    fun startOpinions() {
        _uiState.update {
            it.copy(
                phase = FIRST_OPINION,
                showWhiteIndicator = true,
                showFirstOpinionPercents = true,
                buttonText = R.string.btn_ready
            )
        }
        resetOffsets()
        openCurtains()
        enableController(true)
        enableButton(true)
    }

    fun setQuestion() {
        val states = uiState.value
        val questions = states.questions

        if (questions.isEmpty()) sww()
        else _uiState.update { it.copy(actualQuestion = questions[states.questionPos].question) }
    }

    fun setInitialOffsetsX(value: Float) = _uiState.update { it.copy(initialOffsetX = value, offsetXWhite = value, offsetXRed = value) }
    fun updateOffsetXWhite(value: Float) = _uiState.update { it.copy(offsetXWhite = value) }
    fun updateOffsetXRed(value: Float) = _uiState.update { it.copy(offsetXRed = value) }
    fun resetOffsets() {
        val state = uiState.value
        _uiState.update {
            it.copy(
                offsetXWhite = state.initialOffsetX,
                offsetXRed = state.initialOffsetX
            )
        }
    }

    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }

    fun enableController(value: Boolean) = _uiState.update { it.copy(controllerEnabled = value) }
    fun enableButton(value: Boolean) = _uiState.update { it.copy(buttonEnabled = value) }

    fun openCurtains() {
        _uiState.update {
            it.copy(
                curtainLeftPosition = -500f,
                curtainRightPosition = 500f
            )
        }
    }

    fun closeCurtains() {
        _uiState.update {
            it.copy(
                curtainLeftPosition = 0f,
                curtainRightPosition = 0f
            )
        }
    }

    fun showIndicatorOpinionOne(value: Boolean) = _uiState.update { it.copy(showWhiteIndicator = value) }
    fun showIndicatorOpinionTwo(value: Boolean) = _uiState.update { it.copy(showRedIndicator = value) }

    fun updateFirstOpinionPercents(value: Int) {
        val firstOpBlue = 100 - value
        handleHandsUp(percent = firstOpBlue)

        _uiState.update {
            it.copy(
                firstOpinionBlue = firstOpBlue,
                firstOpinionOrange = value
            )
        }
    }

    fun updateSecondOpinionPercents(value: Int) {
        val secondOpBlue = 100 - value
        handleHandsUp(percent = secondOpBlue)

        _uiState.update {
            it.copy(
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

    fun readyOpinionOne() {
        _uiState.update {
            it.copy(
                showFirstOpinionPercents = false,
                showSecondOpinionPercents = true,
                buttonEnabled = false,
                controllerEnabled = false
            )
        }
        handleHandsUp(50)
        closeCurtains()
        showIndicatorOpinionOne(false)

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    phase = SECOND_OPINION,
                    buttonEnabled = true,
                    controllerEnabled = true,
                    buttonText = R.string.btn_check
                )
            }
            openCurtains()
            showIndicatorOpinionTwo(true)
        }
    }

    fun readyOpinionTwo() {
        val states = uiState.value

        val roundPoints = calculatePoints(states.firstOpinionBlue, states.secondOpinionBlue)

        _uiState.update {
            it.copy(
                points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                controllerEnabled = false,
                phase = if (states.roundCount != 3) NEXT_ROUND else RESULTS,
                buttonEnabled = false,
                showFirstOpinionPercents = true,
                showWhiteIndicator = true,
                questionPos = states.questionPos + 1,
                roundCount = states.roundCount + 1
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    buttonEnabled = true,
                    buttonText = if (states.roundCount != 3) R.string.btn_next_round
                    else R.string.btn_see_result
                )
            }
        }
    }

    fun handleNextRound() {
        closeCurtains()
        handleHandsUp(50)

        _uiState.update {
            it.copy(
                showRoundView = true,
                controllerEnabled = false,
                buttonEnabled = false,
                showFirstOpinionPercents = false,
                showSecondOpinionPercents = false,
                showWhiteIndicator = false,
                showRedIndicator = false,
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showRoundView = false,
                    firstOpinionBlue = 50,
                    secondOpinionBlue = 50,
                    firstOpinionOrange = 50,
                    secondOpinionOrange = 50
                )
            }
            delay(250)
            startOpinions()
        }
    }

    fun showResultDialog(value: Boolean) = _uiState.update { it.copy(showResultDialog = value) }

    fun replay() {
        _uiState.update {
            it.copy(
                roundCount = 0,
                showRoundView = true,
                points = listOf(null, null, null, null),
                showRedIndicator = false,
                showSecondOpinionPercents = false
            )
        }


        startOpinions()
        showResultDialog(false)
    }

    private fun sww() {}
}