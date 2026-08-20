package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components

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
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getAffinity
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getTotalPoints

@Preview
@Composable
fun ResultQuestionsDialogPV() {
    ResultQuestionsDialog(
        listOf(20, 0, 0, 0), "", "Martín",
        {}, {}, {},
    )
}

@Composable
fun ResultQuestionsDialog(
    points: List<Int?>,
    blueName: String,
    orangeName: String,
    exit: () -> Unit,
    replay: () -> Unit,
    changeNames: () -> Unit
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
                SpacerSmall()
                MediumText(text = R.string.endgame_max_points)
                SpacerMini()
                if (totalPoints == 1) {
                    MediumText(text = R.string.endgame_get_one_point)
                } else {
                    MediumText(text = stringResource(R.string.endgame_get_points, totalPoints.toString()))
                }

                SpacerMedium()
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

                SpacerLarge()

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_human_down), null,
                            tint = BlueMedium,
                            modifier = Modifier.size(64.dp)
                        )
                        if (blueName.isBlank()) MediumText(text = R.string.name_blue, color = BlueMedium)
                        else MediumText(text = blueName, color = BlueMedium)

                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_human_down), null,
                            tint = OrangeHard,
                            modifier = Modifier.size(64.dp)
                        )
                        if (orangeName.isBlank()) MediumText(text = R.string.name_orange, color = OrangeHard)
                        else MediumText(text = orangeName, color = OrangeHard)

                    }
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

                ButtonCustom(
                    onClick = { changeNames() },
                    text = R.string.endgame_replay_new_names,
                    color = Black,
                    textColor = White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}