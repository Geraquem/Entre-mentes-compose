package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard

fun calculatePoints(firstOpinion: Int, secondOpinion: Int): Int {
    val diff = kotlin.math.abs(firstOpinion - secondOpinion)
    return when {
        diff > 15 -> 0
        diff == 0 -> 15
        else -> 15 - diff
    }
}

fun getPointsColor(dif: Int): Color {
    return if (dif != 0) GreenMedium else RedHard
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