package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.core.theme.barlow

@Preview
@Composable
fun InitialOfflineDialogPV() {
    InitialOfflineDialog(
        "", {}, "María", {},
        {}, {})
}

@Composable
fun InitialOfflineDialog(
    blueName: String,
    onBlueNameChanged: (String) -> Unit,
    orangeName: String,
    onOrangeNameChanged: (String) -> Unit,
    startGame: () -> Unit,
    howToPlay: () -> Unit
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
                    text = stringResource(R.string.questions_welcome_two),
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

                SpacerMedium()

                MediumText(
                    text = R.string.questions_welcome_four,
                )

                SpacerSmall()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.ic_human_down), null,
                        tint = BlueMedium,
                        modifier = Modifier.size(42.dp)
                    )

                    SpacerSmall(horizontal = true)

                    BasicTextField(
                        modifier = Modifier.fillMaxWidth()
                            .border(width = 1.dp, color = GrayHard, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        value = blueName,
                        onValueChange = { onBlueNameChanged(it.take(20)) },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = BlueMedium,
                            fontSize = 18.sp,
                            fontFamily = alphazet
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        cursorBrush = SolidColor(BlueMedium),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (blueName.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.name_blue),
                                        modifier = Modifier.alpha(0.5f),
                                        color = BlueMedium,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                            innerTextField()
                        }
                    )
                }

                SpacerSmall()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.ic_human_down), null,
                        tint = OrangeHard,
                        modifier = Modifier.size(42.dp)
                    )

                    SpacerSmall(horizontal = true)

                    BasicTextField(
                        modifier = Modifier.fillMaxWidth()
                            .border(width = 1.dp, color = GrayHard, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        value = orangeName,
                        onValueChange = { onOrangeNameChanged(it.take(20)) },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = OrangeHard,
                            fontSize = 18.sp,
                            fontFamily = alphazet
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        cursorBrush = SolidColor(BlueMedium),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (orangeName.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.name_orange),
                                        modifier = Modifier.alpha(0.5f),
                                        color = OrangeHard,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                            innerTextField()
                        }
                    )
                }

                SpacerMedium()

                ButtonCustom(
                    onClick = { startGame() },
                    text = R.string.online_btn_start,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White
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