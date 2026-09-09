package com.mmfsin.betweenminds.presentation.dashboard.ranking.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.GreenMedium
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium

fun calculatePoints(firstList: List<RankingBox>, secondList: List<RankingBox>): Int {
    return 0
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

fun checkBoxColor(
    isEnabled: Boolean,
    firstText: String,
    secondText: String
): Color {
    return if (!isEnabled) GrayHard
    else if (firstText == secondText) GreenMedium else RedMedium
}