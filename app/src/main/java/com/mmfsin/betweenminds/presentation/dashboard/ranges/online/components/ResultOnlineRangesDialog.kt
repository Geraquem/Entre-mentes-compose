package com.mmfsin.betweenminds.presentation.dashboard.ranges.online.components

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.GreenHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.core.theme.august_shining
import com.mmfsin.betweenminds.presentation.dashboard.ranges.helper.getAffinityOffline
import com.mmfsin.betweenminds.presentation.dashboard.ranges.helper.getTotalPoints

@Preview
@Composable
fun ResultOnlineRangesDialogPV() {
    ResultOnlineRangesDialog(listOf(2, 5, 0), 1, {}, {})
}

@Composable
fun ResultOnlineRangesDialog(
    myPoints: List<Int?>,
    otherPlayerPoints: Int,
    exit: () -> Unit,
    replay: () -> Unit,
) {

    val userPoints = getTotalPoints(myPoints)
    val totalPoints = userPoints + otherPlayerPoints
    val affinity = getAffinityOffline(true, totalPoints)

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

                MediumText(
                    text = R.string.endgame_ranges_max_points_online,
                    fontSize = 20.sp,
                )

                SpacerMini()

                BigText(
                    text = R.string.endgame_top_text,
                    fontSize = 20.sp,
                )

                SpacerMedium()

                val text = if (totalPoints == 1) stringResource(R.string.endgame_one_point)
                else stringResource(R.string.endgame_pts, totalPoints.toString())

                BigText(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontFamily = august_shining,
                    fontSize = 32.sp,
                )

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MediumText(text = R.string.endgame_online_ranges_you)

                    Icon(
                        painterResource(R.drawable.ic_arrow), null,
                        modifier = Modifier.graphicsLayer { scaleX = -1f }
                    )

                    SpacerSmall(horizontal = true)

                    val myPointsText = if (userPoints == 1) stringResource(R.string.endgame_one_point)
                    else stringResource(R.string.endgame_pts, userPoints)

                    MediumText(
                        text = myPointsText,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                SpacerMini()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MediumText(text = R.string.endgame_online_ranges_your_teammate)

                    Icon(
                        painterResource(R.drawable.ic_arrow), null,
                        modifier = Modifier.graphicsLayer { scaleX = -1f }
                    )

                    SpacerSmall(horizontal = true)

                    val otherPointsText = if (otherPlayerPoints == 1) stringResource(R.string.endgame_one_point)
                    else stringResource(R.string.endgame_pts, otherPlayerPoints)

                    MediumText(
                        text = otherPointsText,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BigText(text = R.string.endgame_you_have)
                    SpacerSmall(horizontal = true)
                    BigText(
                        text = "$affinity%",
                        color = GreenHard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                    )
                    SpacerSmall(horizontal = true)
                    BigText(text = R.string.endgame_affinity)
                }

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