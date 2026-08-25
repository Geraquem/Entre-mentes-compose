package com.mmfsin.betweenminds.presentation.dashboard.ranges.online

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.OnlineRangeRoundData
import com.mmfsin.betweenminds.domain.models.RangePhaseType
import com.mmfsin.betweenminds.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenminds.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RangesOnlineViewModel @Inject constructor(
    private val getRangesUseCase: GetRangesUseCase,
) : BaseViewModel<RangesOnlineStates>(RangesOnlineStates()) {

    init {
        getRanges()
    }

    private fun getRanges() {
        executeUseCase(
            { getRangesUseCase() },
            { ranges ->
                _uiState.update {
                    it.copy(
                        ranges = ranges,
                        isLoading = false
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
            val newRange = if (states.rangesPos >= states.ranges.size) {
                _uiState.update { it.copy(rangesPos = 0) }
                ranges[0]
            } else ranges[states.rangesPos]

            _uiState.update {
                it.copy(
                    actualRangeLeft = newRange.leftRange,
                    actualRangeRight = newRange.rightRange
                )
            }
        }
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
                buttonText = R.string.online_btn_save_answer,
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

    fun checkBullseyePhase() {
        closeCurtains()

        val states = uiState.value
        val data = OnlineRangeRoundData(
            round = states.roundCount,
            bullseyePosition = states.bullsEyeStart,
            hint = states.hint,
            leftRange = states.actualRangeLeft,
            rightRange = states.actualRangeRight
        )

        _uiState.update {
            it.copy(
                roundData = states.roundData.toMutableList().apply { this[states.roundCount] = data },
                buttonEnabled = false,
                roundCount = states.roundCount + 1,
                rangesPos = states.rangesPos + 1
            )
        }

        if ((states.roundCount) > 1) {
            readyBullseyePhase()
        } else {

            viewModelScope.launch {
                delay(750)
                _uiState.update { it.copy(showRoundView = true) }
                setRange()
                delay(1500)
                _uiState.update {
                    it.copy(
                        hint = "",
                        bullsEyeStart = (0..94).random().toFloat(),
                        showRoundView = false
                    )
                }
                delay(1000)
                openCurtains()
                delay(750)
                _uiState.update { it.copy(buttonEnabled = true) }
            }
        }
    }

    private fun readyBullseyePhase() {
        val states = uiState.value
        _uiState.update {
            it.copy(
                showWaitingOtherPlayerDialog = true,
                phase = RangePhaseType.MOVE_ARROW,
                buttonEnabled = false
            )
        }

        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        println(states.roundData)
        //
        //        viewModelScope.launch {
        //            delay(1500)
        //            _uiState.update {
        //                it.copy(
        //                    showEditTextHint = false,
        //                    showBullseye = false,
        //                    showSlider = true,
        //                    sliderEnabled = true,
        //                    buttonEnabled = true,
        //                    buttonText = R.string.btn_check
        //                )
        //            }
        //            openCurtains()
        //        }
    }

    fun updateHint(value: String) = _uiState.update { it.copy(hint = value) }

    fun updateSliderValue(value: Int) = _uiState.update { it.copy(sliderValue = value.toFloat()) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}