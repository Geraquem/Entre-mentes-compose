package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.domain.models.QuestionRoundType.SECOND_OPINION
import com.mmfsin.betweenmindscompose.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
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

                viewModelScope.launch {
                    delay(1000)
                    _uiState.update { it.copy(showRoundView = false) }
                    delay(1000)
                    openCurtains()
                }
            },
            {}
        )
    }

    fun setInitialOffsetsX(value: Float) = _uiState.update { it.copy(offsetXWhite = value, offsetXRed = value) }
    fun updateOffsetXWhite(value: Float) = _uiState.update { it.copy(offsetXWhite = value) }
    fun updateOffsetXRed(value: Float) = _uiState.update { it.copy(offsetXRed = value) }

    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }

    fun openCurtains() {
        _uiState.update {
            it.copy(
                arrowPointerVisible = true,
                curtainLeftPosition = -500f,
                curtainRightPosition = 500f
            )
        }
    }

    fun closeCurtains() {
        _uiState.update {
            it.copy(
                arrowPointerVisible = false,
                curtainLeftPosition = 0f,
                curtainRightPosition = 0f
            )
        }
    }

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
                firstOpinionVisible = false,
                secondOpinionVisible = true,
            )
        }
        handleHandsUp(50)
        closeCurtains()

        viewModelScope.launch {
            delay(2500)
            _uiState.update {
                it.copy(
                    roundType = SECOND_OPINION
                )
            }
            openCurtains()
        }
    }

    private fun sww() {}
}