package com.mmfsin.betweenminds.presentation.dashboard.ranges.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenminds.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenminds.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenminds.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.ranges.helper.calculateRangePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RangesOfflineViewModel @Inject constructor(
    private val getRangesUseCase: GetRangesUseCase,
) : BaseViewModel<RangesOfflineStates>(RangesOfflineStates()) {

    init {
        getRanges()
    }

    private fun getRanges() {
        executeUseCase(
            { getRangesUseCase() },
            { ranges ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        ranges = ranges
                    )
                }
                setRange()
            },
            { sww() }
        )
    }

    fun setRange() {
        val states = uiState.value
        val ranges = states.ranges

        if (ranges.isEmpty()) sww()
        else {
            val newRange = if (states.ragesPos >= states.ranges.size) {
                _uiState.update { it.copy(ragesPos = 0) }
                ranges[0]
            } else ranges[states.ragesPos]

            _uiState.update {
                it.copy(
                    actualRangeLeft = newRange.leftRange,
                    actualRangeRight = newRange.rightRange
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
            showBullseye()
        }
    }

    fun showBullseye() {
        _uiState.update {
            it.copy(
                bullsEyeStart = (0..94).random().toFloat(),
                phase = SHOW_BULLSEYE,
                buttonText = R.string.btn_ready,
                showSlider = false,
                showEditTextHint = true,
            )
        }
        viewModelScope.launch {
            delay(500)
            _uiState.update { it.copy(buttonEnabled = true) }
        }
        openCurtains()
    }

    fun updateHint(value: String) = _uiState.update { it.copy(hint = value) }

    fun updateSliderValue(value: Int) = _uiState.update { it.copy(sliderValue = value.toFloat()) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun readyBullseyePhase() {
        closeCurtains()
        _uiState.update {
            it.copy(
                phase = MOVE_ARROW,
                buttonEnabled = false
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showEditTextHint = false,
                    showBullseye = false,
                    showSlider = true,
                    sliderEnabled = true,
                    buttonEnabled = true,
                    buttonText = R.string.btn_check
                )
            }
            openCurtains()
        }
    }

    fun readySliderPhase() {
        val states = uiState.value

        val roundPoints = calculateRangePoints(
            sliderPosition = states.sliderValue,
            bullseyeStart = states.bullsEyeStart
        )

        _uiState.update {
            it.copy(
                phase = if (states.roundCount != 0) NEXT_ROUND else RESULTS,
                points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                confettiTrigger = roundPoints,
                showBullseye = true,
                sliderEnabled = false,
                buttonEnabled = false,
                ragesPos = states.ragesPos + 1,
                roundCount = states.roundCount + 1,
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

    fun nextRound() {
        closeCurtains()
        _uiState.update {
            it.copy(
                showRoundView = true,
                buttonEnabled = false,
                sliderEnabled = false,
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showRoundView = false,
                    showSlider = false,
                    sliderValue = 50f,
                    confettiTrigger = 0,
                    showEditTextHint = true,
                    hint = ""
                )
            }
            setRange()

            delay(1000)
            showBullseye()
        }
    }

    fun showResultDialog(value: Boolean) = _uiState.update { it.copy(showResultDialog = value) }

    fun replay() {
        showResultDialog(false)

        _uiState.update {
            it.copy(
                roundCount = 0,
                showRoundView = true,
                points = listOf(null, null, null, null),
                showSlider = false,
                sliderEnabled = false,
                buttonEnabled = false,
            )
        }

        closeCurtains()

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showRoundView = false,
                    sliderValue = 50f,
                    hint = ""
                )
            }

            setRange()
            delay(1000)
            showBullseye()
        }
    }

    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}