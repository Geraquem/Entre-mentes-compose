package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.Range
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.SHOW_BULLSEYE

data class RangesOfflineStates(
    val isLoading: Boolean = true,

    val showInitialDialog: Boolean = true,
    val showResultDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,
    val phase: RangePhaseType = SHOW_BULLSEYE,

    val ranges: List<Range> = emptyList(),
    var ragesPos: Int = 0,
    val actualRangeLeft: String = "",
    val actualRangeRight: String = "",

    val hint: String = "",

    val sliderValue: Float = 50f,
    val bullsEyeStart: Float = 47f,

    val points: List<Int?> = listOf(null, null, null, null),
    val confettiTrigger: Int = 0,

    val curtainsOpen: Boolean = false,

    val showSlider: Boolean = false,
    val showBullseye: Boolean = true,

    val buttonEnabled: Boolean = false,
    val buttonText: Int = R.string.btn_ready
)