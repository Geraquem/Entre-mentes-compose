package com.mmfsin.betweenmindscompose.presentation.questions.offline

import com.mmfsin.betweenmindscompose.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            { questions -> },
            {}
        )
    }

    //    fun onRoomCodeChanged(value: String) = _uiState.update { it.copy(roomCode = value) }

    private fun sww() {}
}