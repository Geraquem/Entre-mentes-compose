package com.mmfsin.betweenminds.presentation.dashboard.questions.online.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.dashboard.questions.offline.components.SetPlayerName

@Preview
@Composable
fun InitialQOCreatorDialogPV() {
    InitialQOCreatorDialog(
        blueName = "Paco",
        onBlueNameChanged = {},
        orangeName = "",
        onOrangeNameChanged = {},
        {}, {}, true
    )
}

@Composable
fun InitialQOCreatorDialog(
    blueName: String,
    onBlueNameChanged: (String) -> Unit,
    orangeName: String,
    onOrangeNameChanged: (String) -> Unit,
    startGame: () -> Unit,
    howToPlay: () -> Unit,
    isLoading: Boolean
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigText(
                text = R.string.selector_questions,
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
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.questions_welcome),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerSmall()

                Text(
                    text = stringResource(R.string.questions_welcome_three_extra),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerSmall()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.questions_welcome_you_are),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 18.sp
                        ),
                    )
                    SpacerSmall(horizontal = true)
                    Image(
                        painterResource(R.drawable.ic_player_one), null,
                        modifier = Modifier.size(32.dp)
                    )
                }

                SpacerSmall()

                Text(
                    text = stringResource(R.string.questions_welcome_you_move_white),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerLarge()

                Text(
                    text = stringResource(R.string.questions_welcome_four),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerSmall()

                SetPlayerName(
                    name = blueName,
                    onNameChange = { onBlueNameChanged(it) },
                    hint = R.string.name_blue,
                    color = BlueMedium
                )

                SpacerSmall()

                SetPlayerName(
                    name = orangeName,
                    onNameChange = { onOrangeNameChanged(it) },
                    hint = R.string.name_orange,
                    color = OrangeHard
                )

                SpacerMedium()

                ButtonCustom(
                    onClick = { startGame() },
                    text = if (isLoading) R.string.online_btn_wait else R.string.online_btn_start,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White,
                    enabled = !isLoading
                )

                SpacerSmall()

                ButtonCustom(
                    onClick = { howToPlay() },
                    text = R.string.ranges_start_instructions,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White
                )
            }
        }
    }
}
