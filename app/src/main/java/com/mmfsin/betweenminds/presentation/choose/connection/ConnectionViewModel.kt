package com.mmfsin.betweenminds.presentation.choose.connection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.GameType.Companion.getGameTypeById
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.usecases.CreateRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRangesPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRankingsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSinglePackUseCase
import com.mmfsin.betweenminds.domain.usecases.JoinRoomUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_ONLINE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRoomUseCase: CreateRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val getSelectedQuestionsPackUseCase: GetSelectedQuestionsPackUseCase,
    private val getSelectedRangesPackUseCase: GetSelectedRangesPackUseCase,
    private val getSelectedRankingsPackUseCase: GetSelectedRankingsPackUseCase,
    private val getSinglePackUseCase: GetSinglePackUseCase,
) : BaseViewModel<ConnectionStates>(ConnectionStates()) {

    private val gameTypeId: String? = savedStateHandle["gameTypeId"]

    init {
        checkGameType()
    }

    private fun checkGameType() {
        if (gameTypeId == null) sww()
        else {
            val type = getGameTypeById(gameTypeId)
            _uiState.update {
                it.copy(
                    gameTypeId = gameTypeId,
                    gameType = type
                )
            }
            setVariableTypes(type)
            getSelectedPack(type)
        }
    }

    private fun setVariableTypes(type: GameType) {
        when (type) {
            QUESTIONS -> {
                _uiState.update {
                    it.copy(
                        packsTab = 0,
                        instructionsNavGraph = NAV_INSTR_QUESTIONS_ONLINE
                    )
                }
            }

            RANGES -> {
                _uiState.update {
                    NAV_INSTR_RANGES_ONLINE
                    it.copy(
                        packsTab = 1,
                        instructionsNavGraph = NAV_INSTR_RANGES_ONLINE
                    )
                }
            }

            RANKING -> {
                _uiState.update {
                    NAV_INSTR_RANGES_ONLINE
                    it.copy(
                        packsTab = 2,
                        instructionsNavGraph = NAV_INSTR_RANKING_ONLINE
                    )
                }
            }
        }
    }

    fun getSelectedPack(gameType: GameType) {
        when (gameType) {
            QUESTIONS -> {
                viewModelScope.launch {
                    getSelectedQuestionsPackUseCase().collect { questionsPackNumber ->
                        executeUseCase(
                            { getSinglePackUseCase(QUESTIONS, questionsPackNumber) },
                            { pack ->
                                if (pack == null) sww()
                                else updateSelectedPack(pack)
                            },
                            { sww() }
                        )
                    }
                }
            }

            RANGES -> {
                viewModelScope.launch {
                    getSelectedRangesPackUseCase().collect { rangesPackNumber ->
                        executeUseCase(
                            { getSinglePackUseCase(RANGES, rangesPackNumber) },
                            { pack ->
                                if (pack == null) sww()
                                else updateSelectedPack(pack)
                            },
                            { sww() }
                        )
                    }
                }
            }

            RANKING -> {
                viewModelScope.launch {
                    getSelectedRankingsPackUseCase().collect { rankingsPackNumber ->
                        executeUseCase(
                            { getSinglePackUseCase(RANKING, rankingsPackNumber) },
                            { pack ->
                                if (pack == null) sww()
                                else updateSelectedPack(pack)
                            },
                            { sww() }
                        )
                    }
                }
            }
        }
    }

    private fun updateSelectedPack(pack: Pack) {
        _uiState.update {
            it.copy(
                packIcon = pack.packIcon,
                packTitle = pack.packTitle,
                isLoading = false
            )
        }
    }

    fun onRoomCodeChanged(value: String) = _uiState.update { it.copy(roomCodeToJoin = value) }

    fun createRoom() {
        showLoading(true)

        val states = uiState.value
        executeUseCase(
            { createRoomUseCase.execute(states.gameTypeId) },
            { code ->
                if (code == null) sww()
                else {
                    _uiState.update { it.copy(roomCodeCreated = code) }
                    createOnlineRoom(true)
                    showLoading(false)
                }
            },
            { sww() }
        )
    }

    fun joinRoom() {
        showLoading(true)
        val states = uiState.value
        executeUseCase(
            {
                joinRoomUseCase.execute(
                    roomId = states.roomCodeToJoin,
                    gameType = states.gameTypeId
                )
            },
            { joined ->
                if (joined) {
                    when (states.gameType) {
                        QUESTIONS -> joinToQuestionsOnline(true)
                        RANGES -> joinToRangesOnline(true)
                        else -> sww()
                    }
                } else joinedError(true)
                showLoading(false)
            },
            {
                joinedError(true)
                showLoading(false)
            }
        )
    }

    fun playOffline() {
        val gameType = uiState.value.gameType
        gameType?.let { type ->
            when (type) {
                QUESTIONS -> startQuestionsOffline(true)
                RANGES -> startRangesOffline(true)
                RANKING -> {}
            }
        }
    }

    fun createOnlineRoom(value: Boolean) = _uiState.update { it.copy(createOnlineRoom = value) }

    fun joinToQuestionsOnline(value: Boolean) = _uiState.update { it.copy(joinToQuestionsOnline = value) }
    fun joinToRangesOnline(value: Boolean) = _uiState.update { it.copy(joinToRangesOnline = value) }

    fun startQuestionsOffline(value: Boolean) = _uiState.update { it.copy(startQuestionsOffline = value) }
    fun startRangesOffline(value: Boolean) = _uiState.update { it.copy(startRangesOffline = value) }

    fun showLoading(value: Boolean) = _uiState.update { it.copy(isLoading = value) }

    fun joinedError(value: Boolean) = _uiState.update { it.copy(showErrorJoinedDialog = value) }
    fun sww(value: Boolean = true) = _uiState.update { it.copy(showSwwDialog = value) }
}