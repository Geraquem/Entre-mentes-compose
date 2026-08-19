package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.theme.Background
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.helper.getPointsColor

@Preview
@Composable
fun RangeRoundsPV() {
    RangeRounds(listOf(1, 5, 2, null))
}

@Composable
fun RangeRounds(points: List<Int?>) {
    Column {
        MediumText(
            text = stringResource(R.string.scoreboard_rounds),
            fontFamily = alphazet,
            color = White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        SpacerMini()

        Row(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Background)
                .padding(8.dp)
        ) {
            points.take(4).forEachIndexed { i, p ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = p,
                        label = "point_animation"
                    ) { point ->
                        if (point == null) {
                            BigText(
                                text = "${i + 1}",
                                color = GrayHard,
                                fontFamily = alphazet,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            BigText(
                                text = if (p == 1) "$p pt" else "$p pts",
                                color = getPointsColor(point)
                            )
                        }
                    }
                }
            }
        }
    }
}