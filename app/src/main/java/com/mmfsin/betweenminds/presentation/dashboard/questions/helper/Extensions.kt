package com.mmfsin.betweenminds.presentation.dashboard.questions.helper

import androidx.compose.ui.graphics.Color
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.presentation.core.theme.GreenHard
import com.mmfsin.betweenminds.presentation.core.theme.GreenMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard

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