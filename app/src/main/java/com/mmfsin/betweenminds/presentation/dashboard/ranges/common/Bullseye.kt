package com.mmfsin.betweenminds.presentation.dashboard.ranges.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.GreenHard
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.manaspace

@Preview
@Composable
fun BullseyePV() {
    Bullseye(50f)
}

@Composable
fun Bullseye(bullsEyeStart: Float) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val bullseyeStart = (bullsEyeStart - 10f) / 100f
        val bullseyeWidth = 26f / 100f

        Row(
            modifier = Modifier
                .fillMaxWidth(bullseyeWidth)
                .offset(x = maxWidth * bullseyeStart),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BullseyeNumber(
                number = 1,
                weight = 5f,
                color = BlueMedium
            )

            BullseyeNumber(
                number = 2,
                weight = 5f,
                color = OrangeHard
            )

            BullseyeNumber(
                number = 5,
                weight = 6f,
                color = GreenHard
            )

            BullseyeNumber(
                number = 2,
                weight = 5f,
                color = OrangeHard
            )

            BullseyeNumber(
                number = 1,
                weight = 5f,
                color = BlueMedium
            )
        }
    }
}

@Composable
private fun RowScope.BullseyeNumber(
    number: Int,
    weight: Float,
    color: Color
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        MediumText(
            text = number.toString(),
            fontFamily = manaspace
        )
    }
}