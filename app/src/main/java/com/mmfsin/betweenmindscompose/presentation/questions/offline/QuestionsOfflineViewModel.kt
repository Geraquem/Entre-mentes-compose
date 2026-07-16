package com.mmfsin.betweenmindscompose.presentation.questions.offline

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenmindscompose.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenmindscompose.presentation.choose.ChooseStates
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuestionsOfflineViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<QuestionsOfflineStates>(QuestionsOfflineStates()) {

    private val packId: String? = savedStateHandle["packId"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
    }

//    fun onRoomCodeChanged(value: String) = _uiState.update { it.copy(roomCode = value) }

    private fun sww() {}
}