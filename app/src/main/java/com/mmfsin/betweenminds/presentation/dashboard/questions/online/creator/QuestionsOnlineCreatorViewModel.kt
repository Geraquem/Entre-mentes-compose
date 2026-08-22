package com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.usecases.GetQuestionsUseCase
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
                //                setQuestion()
                setDataInRoom()
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
//            startOpinions()
        }
    }


    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }


    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}