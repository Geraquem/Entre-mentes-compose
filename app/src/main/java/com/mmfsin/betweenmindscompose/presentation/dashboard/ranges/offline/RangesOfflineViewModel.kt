package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenmindscompose.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
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

        /** delete */
        hideInitialDialog()
        /****/
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
                phase = SHOW_BULLSEYE,
                buttonText = R.string.btn_ready,
                controllerEnabled = false,
                buttonEnabled = true,
            )
        }
        openCurtains()
    }

    fun updateHint(value: String) = _uiState.update { it.copy(hint = value) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    private fun sww() {}
}