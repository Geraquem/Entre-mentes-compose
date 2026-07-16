package com.mmfsin.betweenmindscompose.presentation.choose

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenmindscompose.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChooseStates>(ChooseStates()) {

    private val gameType: String? = savedStateHandle["gameType"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
        if (gameType == null) sww()
        else {
            val type = getGameTypeById(gameType)
            _uiState.update { it.copy(gameType = type) }
        }
    }

    fun onRoomCodeChanged(value: String) = _uiState.update { it.copy(roomCode = value) }

    private fun sww() {}
}