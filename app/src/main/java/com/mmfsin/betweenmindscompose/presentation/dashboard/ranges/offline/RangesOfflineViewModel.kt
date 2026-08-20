package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenmindscompose.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.helper.calculateRangePoints
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
                _uiState.update { it.copy(ranges = ranges) }
                setRange()
            },
            {}
        )
    }

    fun setRange() {
        val states = uiState.value
        val ranges = states.ranges

        if (ranges.isEmpty()) sww()
        else _uiState.update {
            it.copy(
                actualRangeLeft = ranges[states.ragesPos].leftRange,
                actualRangeRight = ranges[states.ragesPos].rightRange
            )
        }
    }

    fun hideInitialDialog() {
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            //            delay(1000)
            delay(10)
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
                phase = if (states.roundCount != 3) NEXT_ROUND else RESULTS,
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

    }

    private fun sww() {}
}