@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.dashboard.questions.online.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.GrayMedium
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun InitialQOJoinDialogPV() {
    InitialQOJoinDialog(
        {}, {}, {}, true
    )
}

@Composable
fun InitialQOJoinDialog(
    startGame: () -> Unit,
    howToPlay: () -> Unit,
    exit: () -> Unit,
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
                        painterResource(R.drawable.ic_player_two), null,
                        modifier = Modifier.size(32.dp)
                    )
                }

                SpacerSmall()

                Text(
                    text = stringResource(R.string.questions_welcome_you_move_red),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerMedium()

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GrayMedium)
                ) {
                    Slider(
                        modifier = Modifier.fillMaxWidth(),
                        value = 50f,
                        onValueChange = {},
                        valueRange = 0f..100f,
                        enabled = false,
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .fillMaxHeight()
                                    .background(RedMedium)
                            )
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = White,
                            activeTrackColor = Transparent,
                            disabledActiveTrackColor = Transparent,
                            inactiveTrackColor = Transparent,
                            disabledInactiveTrackColor = Transparent,
                        ),
                    )
                }

                SpacerLarge()


                ButtonCustom(
                    onClick = { startGame() },
                    text = if (isLoading) R.string.online_btn_wait else R.string.online_btn_start,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White,
                    enabled = !isLoading
                )

                SpacerSmall()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    ButtonCustom(
                        onClick = { exit() },
                        text = R.string.exit,
                        color = Black,
                        textColor = White
                    )

                    SpacerMini(horizontal = true)

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
}
