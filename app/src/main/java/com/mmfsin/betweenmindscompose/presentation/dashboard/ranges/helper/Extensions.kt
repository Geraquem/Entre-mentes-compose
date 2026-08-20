package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard

fun calculateRangePoints(sliderPosition: Float, bullseyeStart: Float): Int {
    return when (sliderPosition) {
        in bullseyeStart..<(bullseyeStart + 6f) -> 5
        in (bullseyeStart - 5f)..<bullseyeStart -> 2
        in (bullseyeStart + 6f)..<(bullseyeStart + 11f) -> 2
        in (bullseyeStart - 10f)..<(bullseyeStart - 5f) -> 1
        in (bullseyeStart + 11f)..<(bullseyeStart + 16f) -> 1
        else -> 0
    }
}

fun getPointsColor(points: Int): Color {
    return when (points) {
        1 -> BlueMedium
        2 -> OrangeHard
        5 -> GreenMedium
        else -> RedHard
    }
}

fun getTotalPoints(points: List<Int?>): Int {
    var result = 0
    points.forEach { p -> if (p != null) result += p }
    return result
}

fun getAffinity(points: Int): String {
    val value = (points.toFloat() / 60f) * 100f
    return if (value % 1f == 0f) value.toInt().toString()
    else "%.2f".format(value)
}