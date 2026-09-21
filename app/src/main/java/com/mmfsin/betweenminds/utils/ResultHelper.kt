package com.mmfsin.betweenminds.utils

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.presentation.core.theme.GreenHard
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard

fun getAffinity(
    type: GameType,
    points: Int,
    isOnline: Boolean
): Pair<Float, String> {
    val percentage = when (type) {
        GameType.QUESTIONS -> points.toFloat() / 60f

        GameType.RANGES -> {
            val totalScore = if (isOnline) 30f else 20f
            points.toFloat() / totalScore
        }

        GameType.RANKING -> {
            val totalScore = if (isOnline) 24f else 16f
            points.toFloat() / totalScore
        }
    }

    val percenStr = getAffinityStr(percentage)
    return Pair(percentage, percenStr)
}

fun getAffinityStr(aff: Float): String {
    val value = aff * 100f
    return if (value % 1f == 0f) value.toInt().toString()
    else "%.2f".format(value)
}

fun getPercentageColor(percentage: Float): Color {
    return when (percentage) {
        in (0f..0.24f) -> RedHard
        in (0.25f..0.59f) -> OrangeHard
        else -> GreenHard
    }
}