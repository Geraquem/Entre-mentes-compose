package com.mmfsin.betweenminds.presentation.dashboard.ranking.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.GreenMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium

fun calculatePoints(firstList: List<String>, secondList: List<String>): Int {
    return firstList.zip(secondList).count { (correct, user) -> correct == user }
}

fun getPointsColor(dif: Int): Color {
    return when (dif) {
        1,2 -> OrangeHard
        4 -> GreenMedium
        else -> RedHard
    }
}

fun getAffinity(isOnline: Boolean, points: Int): String {
    val totalScore = if (isOnline) 24f else 16f
    val value = (points.toFloat() / totalScore) * 100f
    return if (value % 1f == 0f) value.toInt().toString()
    else "%.2f".format(value)
}

fun checkBoxColor(
    isEnabled: Boolean,
    firstText: String,
    secondText: String
): Color {
    return if (!isEnabled) GrayHard
    else if (firstText == secondText) GreenMedium else RedMedium
}