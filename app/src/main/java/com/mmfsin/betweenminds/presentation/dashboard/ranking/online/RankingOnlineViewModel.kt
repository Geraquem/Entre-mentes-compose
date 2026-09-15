package com.mmfsin.betweenminds.presentation.dashboard.ranking.online

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.OnlineRankingData
import com.mmfsin.betweenminds.domain.models.OnlineRankingRoundData
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_FIRST
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_SECOND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.RESULTS
import com.mmfsin.betweenminds.domain.models.emptyRankingBoxList
import com.mmfsin.betweenminds.domain.usecases.GetRankingDataUseCase
import com.mmfsin.betweenminds.domain.usecases.RestartGameAndResetRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.SendMyORankingDataToRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.SendMyRankingsPointsUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitCreatorToRestartORangesUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitOtherPlayerORankingsUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitOtherPlayerRankingsPointsUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.ranking.helper.calculatePoints
import com.mmfsin.betweenminds.utils.getTotalPoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingOnlineViewModel @Inject constructor(
    private val getRankingDataUseCase: GetRankingDataUseCase,
    private val sendMyORankingDataToRoomUseCase: SendMyORankingDataToRoomUseCase,
    private val waitOtherPlayerORankingsUseCase: WaitOtherPlayerORankingsUseCase,
    private val sendMyRankingsPointsUseCase: SendMyRankingsPointsUseCase,
    private val waitOtherPlayerRankingsPointsUseCase: WaitOtherPlayerRankingsPointsUseCase,
    private val waitCreatorToRestartORangesUseCase: WaitCreatorToRestartORangesUseCase,
    private val restartGameAndResetRoomUseCase: RestartGameAndResetRoomUseCase
) : BaseViewModel<RankingOnlineStates>(RankingOnlineStates()) {

    init {
        getRankings()
    }

    fun updateRoomCodeAndStatus(code: String?, isCreator: Boolean?) {
        if (code == null || isCreator == null) {
            sww()
        } else _uiState.update {
            it.copy(
                roomCode = code,
                isCreator = isCreator
            )
        }
    }

    fun getRankings() {
        executeUseCase(
            { getRankingDataUseCase() },
            { rankings ->
                _uiState.update {
                    it.copy(
                        rankings = rankings,
                        isLoading = false
                    )
                }

                setRanking()
            },
            { sww() }
        )
    }

    private fun setRanking() {
        val states = uiState.value
        val rankings = states.rankings

        if (rankings.isEmpty()) sww()
        else {
            val newRanking = if (states.rankingPos >= states.rankings.size) {
                _uiState.update { it.copy(rankingPos = 0) }
                rankings[0]
            } else rankings[states.rankingPos]

            _uiState.update {
                it.copy(
                    actualRankingText = newRanking.text,
                    actualRankings = newRanking.rankings.toMutableList(),
                    actualRankingsAux = newRanking.rankings.toMutableList(),
                )
            }
        }
    }

    fun hideInitialDialog() {
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            delay(1)
            _uiState.update { it.copy(showRoundView = false) }
        }
    }

    fun swapTexts(targetIndex: Int, sourceIndex: Int) {
        val states = uiState.value
        val boxItem = states.rankingBoxList[targetIndex]
        val optionItem = states.actualRankings[sourceIndex]

        if (optionItem != "") {
            states.rankingBoxList[targetIndex] = boxItem.copy(text = optionItem)
            states.actualRankings[sourceIndex] = boxItem.text
        }
    }

    fun checkFirstPhase() {
        val states = uiState.value

        if (!(states.rankingBoxList.any { it.text.isEmpty() })) {

            val data = OnlineRankingRoundData(
                round = states.roundCount,
                text = states.actualRankingText,
                rankingTexts = states.actualRankingsAux,
                rankingsSorted = states.rankingBoxList
            )

            _uiState.update { it.copy(roundData = states.roundData.toMutableList().apply { this[states.roundCount] = data }) }

            if ((states.roundCount) > 1) {
                readyFirstPhase()
            } else {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            showRoundView = true,
                            roundCount = states.roundCount + 1,
                            rankingPos = states.rankingPos + 1,
                            buttonEnabled = false,
                            dragEnabled = false,
                        )
                    }
                    delay(1500)
                    setRanking()
                    _uiState.update {
                        it.copy(
                            showRoundView = false,
                            rankingBoxList = emptyRankingBoxList(),
                        )
                    }
                    delay(750)
                    _uiState.update {
                        it.copy(
                            dragEnabled = true,
                            buttonEnabled = true
                        )
                    }
                }
            }
        }
    }

    fun readyFirstPhase() {
        val states = uiState.value
        _uiState.update {
            it.copy(
                showWaitingOtherPlayerDialog = true,
                phase = ORDER_SECOND,
                buttonEnabled = false,
                dragEnabled = false,
                rankingBoxList = emptyRankingBoxList(),
            )
        }

        if (states.roundData.all { it != null }) {
            val myData = OnlineRankingData(
                roomId = states.roomCode,
                isCreator = states.isCreator,
                data = states.roundData.filterNotNull()
            )
            executeUseCase(
                { sendMyORankingDataToRoomUseCase.execute(myData) },
                { waitForOtherPlayerData() },
                { sww() }
            )
        } else sww()
    }

    private fun waitForOtherPlayerData() {
        val states = uiState.value
        executeUseCase(
            {
                waitOtherPlayerORankingsUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = states.isCreator
                )
            },
            { data ->
                _uiState.update {
                    it.copy(
                        otherPlayerData = data,
                        showWaitingOtherPlayerDialog = false
                    )
                }
                if (data.isEmpty()) sww() else startSecondPhase()
            },
            { sww() }
        )
    }

    private fun startSecondPhase() {
        viewModelScope.launch {
            _uiState.update { it.copy(showOtherPlayerDataDialog = true) }
            delay(2500)
            _uiState.update {
                it.copy(
                    buttonText = R.string.btn_check,
                    buttonEnabled = true,
                    dragEnabled = true,
                    roundCount = 0,
                    showOtherPlayerDataDialog = false,
                )
            }
            setOtherPlayerData()
        }
    }

    fun setOtherPlayerData() {
        val states = uiState.value
        val newRange = states.otherPlayerData[states.roundCount]
        _uiState.update {
            it.copy(
                actualRankingText = newRange.text,
                actualRankings = newRange.rankingTexts.toMutableList(),
                firstSortedList = newRange.rankingsSorted.toMutableList()
            )
        }
    }

    fun checkSecondPhase() {
        val states = uiState.value
        if (!(states.rankingBoxList.any { it.text.isEmpty() })) {

            val roundPoints = calculatePoints(
                firstList = states.firstSortedList.map { it.text },
                secondList = states.rankingBoxList.map { it.text }
            )

            _uiState.update {
                it.copy(
                    buttonEnabled = false,
                    dragEnabled = false,

                    secondSortedList = states.rankingBoxList,

                    points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                    confettiTrigger = roundPoints,
                    shakeTrigger = roundPoints == 0,

                    showComparativeList = true,
                    shakeVerticalTrigger = false,

                    phase = if (states.roundCount != 2) NEXT_ROUND else RESULTS,

                    roundCount = states.roundCount + 1
                )
            }

            viewModelScope.launch {
                delay(1500)
                _uiState.update {
                    it.copy(
                        buttonEnabled = true,
                        buttonText = if (states.roundCount != 2) R.string.btn_next_round else R.string.btn_see_result
                    )
                }
            }
        }
    }

    fun handleNextRoundSecondPhase() {
        _uiState.update {
            it.copy(
                showRoundView = true,
                buttonEnabled = false,
                dragEnabled = false,
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showComparativeList = false,
                    confettiTrigger = 0,
                    rankingBoxList = emptyRankingBoxList(),
                    phase = ORDER_SECOND,
                    buttonText = R.string.btn_check,
                    showRoundView = false,
                )
            }
            setOtherPlayerData()

            delay(250)

            _uiState.update {
                it.copy(
                    buttonEnabled = true,
                    dragEnabled = true
                )
            }
        }
    }

    fun sendMyResult() {
        _uiState.update { it.copy(showWaitingOtherPlayerDialog = true) }
        val states = uiState.value
        val points = getTotalPoints(states.points)

        executeUseCase(
            {
                sendMyRankingsPointsUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = states.isCreator,
                    points = points
                )
            },
            { waitToOtherPlayerResult() },
            { sww() }
        )
    }

    private fun waitToOtherPlayerResult() {
        val states = uiState.value
        executeUseCase(
            {
                waitOtherPlayerRankingsPointsUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = states.isCreator
                )
            },
            { otherPlayerPoints ->
                _uiState.update {
                    it.copy(
                        otherPlayerPoints = otherPlayerPoints,
                        showWaitingOtherPlayerDialog = false,
                        showResultDialog = true
                    )
                }
            },
            { sww() }
        )
    }

    fun replay() {
        val states = uiState.value
        if (states.isCreator) {
            executeUseCase(
                { restartGameAndResetRoomUseCase.execute(states.roomCode) },
                { gameRestarted() },
                { sww() }
            )
        } else {
            _uiState.update {
                it.copy(
                    showResultDialog = false,
                    showWaitingOtherPlayerDialog = true
                )
            }
            executeUseCase(
                { waitCreatorToRestartORangesUseCase.execute(states.roomCode) },
                { gameRestarted() },
                { sww() }
            )
        }
    }

    fun gameRestarted() {
        val states = uiState.value
        _uiState.update {
            it.copy(
                showResultDialog = false,
                showWaitingOtherPlayerDialog = false,
                phase = ORDER_FIRST,
                roundCount = 0,
                showRoundView = true,
                points = listOf(null, null, null),
                otherPlayerPoints = 0,
                rankingPos = states.rankingPos + 1,
                buttonEnabled = false,
                dragEnabled = false
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showRoundView = false,
                    actualRankingText = "",
                    showComparativeList = false,

                    actualRankings = listOf("", "", "", "").toMutableList(),
                    actualRankingsAux = listOf("", "", "", "").toMutableList(),

                    rankingBoxList = emptyRankingBoxList(),
                    firstSortedList = emptyRankingBoxList(),
                    secondSortedList = emptyRankingBoxList(),

                    buttonEnabled = true,
                    dragEnabled = true,
                    buttonText = R.string.online_btn_save_answer
                )
            }

            delay(1000)
            setRanking()
        }
    }

    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}