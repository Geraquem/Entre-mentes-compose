package com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineCreatorViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : BaseViewModel<QuestionsOnlineCreatorStates>(QuestionsOnlineCreatorStates()) {

    init {
        getQuestions()
    }

    private fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase() },
            { questions ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        questions = questions
                    )
                }
                setQuestion()
            },
            { sww() }
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
                buttonText = R.string.btn_ready,
                controllerEnabled = true,
                buttonEnabled = true,
            )
        }
//        openCurtains()
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
        }
    }

    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }





    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}