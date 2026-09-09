package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_SECOND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.RESULTS
import com.mmfsin.betweenminds.domain.usecases.GetRankingDataUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.ranking.helper.calculatePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingOfflineViewModel @Inject constructor(
    private val getRankingDataUseCase: GetRankingDataUseCase
) : BaseViewModel<RankingOfflineStates>(RankingOfflineStates()) {

    init {
        getRankings()
    }

    fun getRankings() {
        executeUseCase(
            { getRankingDataUseCase() },
            { rankings ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rankings = rankings
                    )
                }
                initializeRankingBoxList()
                setRanking()
            },
            { sww() }
        )
    }

    fun initializeRankingBoxList() {
        val initialList = mutableStateListOf(
            RankingBox(id = 0, text = ""),
            RankingBox(id = 1, text = ""),
            RankingBox(id = 2, text = ""),
            RankingBox(id = 3, text = ""),
        )
        _uiState.update { it.copy(rankingBoxList = initialList) }
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
                    actualRankingTopText = newRanking.topText,
                    actualRankingBottomText = newRanking.bottomText,
                    actualRankings = newRanking.rankings.toMutableList(),
                    actualRankingsAux = newRanking.rankings.toMutableList()
                )
            }
        }
    }

    fun hideInitialDialog() {
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(showRoundView = false) }
            delay(1000)
        }
    }

    fun swapTexts(targetIndex: Int, sourceIndex: Int) {
        val states = uiState.value
        val oldItem = states.rankingBoxList[targetIndex]
        val option = states.actualRankings[sourceIndex]

        states.rankingBoxList[targetIndex] = oldItem.copy(text = option)
        states.actualRankings[sourceIndex] = oldItem.text
    }

    fun readyOrderOne() {
        val states = uiState.value
        if (!(states.rankingBoxList.any { it.text.isEmpty() })) {
            _uiState.update {
                it.copy(
                    phase = ORDER_SECOND,
                    buttonEnabled = false,
                    buttonText = R.string.btn_check,
                    actualRankings = states.actualRankingsAux,
                    sortedListOne = states.rankingBoxList
                )
            }
            initializeRankingBoxList()

            viewModelScope.launch {
                delay(1000)
                _uiState.update {
                    it.copy(
                        buttonEnabled = true,
                    )
                }
            }
        }
    }

    fun readyOrderTwo() {
        val states = uiState.value
        if (!(states.rankingBoxList.any { it.text.isEmpty() })) {

            /** calculate points */
            val roundPoints = calculatePoints(states.sortedListOne, states.rankingBoxList)

            _uiState.update {
                it.copy(
                    buttonEnabled = false,

                    points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                    confettiTrigger = roundPoints,
                    shakeTrigger = roundPoints == 0,

                    phase = if (states.roundCount != 3) NEXT_ROUND else RESULTS,

                    rankingPos = states.rankingPos + 1,
                    roundCount = states.roundCount + 1
                )
            }

            viewModelScope.launch {
                delay(1500)
                _uiState.update {
                    it.copy(
                        buttonEnabled = true,
                        buttonText = if (states.roundCount != 3) R.string.btn_next_round else R.string.btn_see_result
                    )
                }
            }
        }
    }

    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}