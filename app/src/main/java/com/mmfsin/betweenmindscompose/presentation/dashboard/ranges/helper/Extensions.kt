package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

fun calculateRangePoints(sliderPosisition: Float, bullseyeStart: Float): Int {
    return 1
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