package com.mmfsin.betweenminds.presentation.dashboard.ranges.online

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.OnlineRangeRoundData
import com.mmfsin.betweenminds.domain.models.OnlineRangesData
import com.mmfsin.betweenminds.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenminds.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenminds.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenminds.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenminds.domain.usecases.SendMyORangesDataToRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitOtherPlayerORangesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.ranges.helper.calculateRangePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RangesOnlineViewModel @Inject constructor(
    private val getRangesUseCase: GetRangesUseCase,
    private val sendMyORangesDataToRoomUseCase: SendMyORangesDataToRoomUseCase,
    private val waitOtherPlayerORangesUseCase: WaitOtherPlayerORangesUseCase,
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

        _uiState.update { it.copy(roundData = states.roundData.toMutableList().apply { this[states.roundCount] = data }) }

        if ((states.roundCount) > 1) {
            readyBullseyePhase()
        } else {
            viewModelScope.launch {
                delay(1000)
                _uiState.update {
                    it.copy(
                        roundCount = states.roundCount + 1,
                        rangesPos = states.rangesPos + 1,
                        showRoundView = true,
                        buttonEnabled = false,
                    )
                }
                delay(1500)
                setRange()
                _uiState.update {
                    it.copy(
                        hint = "",
                        bullsEyeStart = (0..94).random().toFloat(),
                        showRoundView = false,
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
                hint = "",
                showBullseye = false,
                phase = MOVE_ARROW,
                buttonEnabled = false,
                showWaitingOtherPlayerDialog = true,
            )
        }

        if (states.roundData.all { it != null }) {
            val myData = OnlineRangesData(
                roomId = states.roomCode,
                isCreator = states.isCreator,
                data = states.roundData.filterNotNull()
            )
            executeUseCase(
                { sendMyORangesDataToRoomUseCase.execute(myData) },
                { waitForOtherPlayerData() },
                { sww() }
            )
        } else sww()
    }

    private fun waitForOtherPlayerData() {
        val states = uiState.value
        executeUseCase(
            {
                waitOtherPlayerORangesUseCase.execute(
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
                if (data.isEmpty()) sww() else startGuessingPhase()
            },
            { sww() }
        )
    }

    private fun startGuessingPhase() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showEditTextHint = false,
                    roundCount = 0,
                    buttonEnabled = false,
                    sliderEnabled = false,
                    phase = MOVE_ARROW,
                    showOtherPlayerRangesDialog = true,
                )
            }
            delay(2500)
            setOtherPlayerData()
            _uiState.update {
                it.copy(
                    showSlider = true,
                    sliderEnabled = true,
                    buttonEnabled = true,
                )
            }
            openCurtains()
        }
    }

    fun setOtherPlayerData() {
        val states = uiState.value

        val newRange = states.otherPlayerData[states.roundCount]

        _uiState.update {
            it.copy(
                hint = newRange.hint,
                bullsEyeStart = newRange.bullseyePosition,
                actualRangeLeft = newRange.leftRange,
                actualRangeRight = newRange.rightRange,
                buttonText = R.string.btn_check,
                showOtherPlayerRangesDialog = false
            )
        }
    }

    fun checkSliderPhase() {
        val states = uiState.value

        val roundPoints = calculateRangePoints(
            sliderPosition = states.sliderValue,
            bullseyeStart = states.bullsEyeStart
        )

        _uiState.update {
            it.copy(
                phase = if (states.roundCount != 2) NEXT_ROUND else RESULTS,
                points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                confettiTrigger = roundPoints,
                showBullseye = true,
                sliderEnabled = false,
                buttonEnabled = false,
                roundCount = states.roundCount + 1,
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
            setOtherPlayerData()
            _uiState.update {
                it.copy(
                    showBullseye = false,
                    sliderValue = 50f,
                    confettiTrigger = 0,
                    showSlider = true,
                    sliderEnabled = true,
                    buttonEnabled = true,
                    phase = MOVE_ARROW,
                    showRoundView = false,
                )
            }
            delay(1000)
            openCurtains()
        }
    }

    fun updateHint(value: String) = _uiState.update { it.copy(hint = value) }

    fun updateSliderValue(value: Int) = _uiState.update { it.copy(sliderValue = value.toFloat()) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showOtherPlayerRangesDialog(value: Boolean) = _uiState.update { it.copy(showOtherPlayerRangesDialog = value) }
    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}