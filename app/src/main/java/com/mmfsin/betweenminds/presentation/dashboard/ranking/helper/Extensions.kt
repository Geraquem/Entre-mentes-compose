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

fun checkBoxColor(
    isEnabled: Boolean,
    firstText: String,
    secondText: String
): Color {
    return if (!isEnabled) GrayHard
    else if (firstText == secondText) GreenMedium else RedMedium
}