package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.core.theme.august_shining
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getAffinity
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getTotalPoints

@Preview
@Composable
fun ResultRangesDialogPV() {
    ResultRangesDialog(listOf(2, 0, 0, 0), {}, {})
}

@Composable
fun ResultRangesDialog(
    points: List<Int?>,
    exit: () -> Unit,
    replay: () -> Unit,
) {

    val totalPoints = getTotalPoints(points)
    val affinity = getAffinity(totalPoints)

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigText(
                text = R.string.endgame_result,
                allCaps = true,
                color = White,
                fontFamily = alphazet,
                fontSize = 30.sp
            )

            SpacerSmall()

            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerLarge()

                BigText(
                    text = R.string.endgame_top_text,
                    fontSize = 20.sp,
                )

                SpacerSmall()

                val text = if (totalPoints == 1) stringResource(R.string.endgame_one_point)
                else stringResource(R.string.endgame_pts, totalPoints.toString())

                BigText(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontFamily = august_shining,
                    fontSize = 32.sp,
                )

                SpacerCustom(34.dp)

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { exit() }) {
                        Icon(
                            painterResource(R.drawable.ic_exit), null,
                            tint = RedHard,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    SpacerMedium(horizontal = true)

                    ButtonCustom(
                        onClick = { replay() },
                        text = R.string.endgame_replay,
                        color = Black,
                        textColor = White,
                        modifier = Modifier.weight(1f)
                    )
                }

                SpacerSmall()
            }
        }
    }
}