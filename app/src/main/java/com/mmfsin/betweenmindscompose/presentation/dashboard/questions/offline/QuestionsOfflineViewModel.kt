package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.copy

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

    fun updateOffsetX(value: Float) {
        println("---------------------------- $value")
        _uiState.update { it.copy(offsetX = value) }
    }

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
        _uiState.update {
            it.copy(
                firstOpinionBlue = firstOpBlue,
                firstOpinionOrange = value
            )
        }
    }

    private fun sww() {}
}