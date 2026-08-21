package com.mmfsin.betweenminds.presentation.choose.createroom

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CreateRoomStates>(CreateRoomStates()) {

    private val roomCode: String? = savedStateHandle["roomCode"]
    private val gameTypeId: String? = savedStateHandle["gameTypeId"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
        if (roomCode == null) sww()
        else _uiState.update { it.copy(roomCode = roomCode) }
    }

    private fun sww() {}
}