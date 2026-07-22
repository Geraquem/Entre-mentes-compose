package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.kineks

@Preview
@Composable
fun PeoplePV() {
    People(
        "Joaquin", {}, 55, 90,
        "Sandra", {}, 45, 10,
        true, true, true, true
    )
}

@Composable
fun People(
    blueName: String,
    onBlueNameChange: (String) -> Unit,
    firstBlueOpinion: Int,
    secondBlueOpinion: Int,
    orangeName: String,
    onOrangeNameChange: (String) -> Unit,
    firstOrangeOpinion: Int,
    secondOrangeOpinion: Int,
    showFirstOpinion: Boolean,
    showSecondOpinion: Boolean,
    blueHandsUp: Boolean,
    orangeHandsUp: Boolean,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val blueIcon = if (blueHandsUp) R.drawable.ic_human_up else R.drawable.ic_human_down
            val orangeIcon = if (orangeHandsUp) R.drawable.ic_human_up else R.drawable.ic_human_down

            Icon(
                painterResource(blueIcon), null,
                modifier = Modifier.size(50.dp),
                tint = BlueMedium
            )
            Icon(
                painterResource(orangeIcon), null,
                modifier = Modifier.size(50.dp),
                tint = OrangeMedium
            )
        }
        SpacerSmall()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BasicTextField(
                value = blueName,
                onValueChange = { onBlueNameChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = BlueMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),

                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
            )
            BasicTextField(
                value = orangeName,
                onValueChange = { onOrangeNameChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = OrangeMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),

                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
            )
        }

        SpacerMedium()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (showFirstOpinion) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = firstBlueOpinion.toString(),
                            color = BlueMedium,
                            fontSize = 26.sp,
                            fontFamily = kineks
                        )

                        Text(
                            text = "%",
                            color = BlueMedium,
                            fontSize = 20.sp,
                            fontFamily = kineks
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_question),
                        contentDescription = null,
                        tint = BlueMedium,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_player_one),
                contentDescription = null
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (showFirstOpinion) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = firstOrangeOpinion.toString(),
                            color = OrangeMedium,
                            fontSize = 26.sp,
                            fontFamily = kineks
                        )

                        Text(
                            text = "%",
                            color = OrangeMedium,
                            fontSize = 20.sp,
                            fontFamily = kineks
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_question),
                        contentDescription = null,
                        tint = OrangeMedium,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        SpacerSmall()


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (showSecondOpinion) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = secondBlueOpinion.toString(),
                            color = BlueMedium,
                            fontSize = 26.sp,
                            fontFamily = kineks
                        )

                        Text(
                            text = "%",
                            color = BlueMedium,
                            fontSize = 20.sp,
                            fontFamily = kineks
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_question),
                        contentDescription = null,
                        tint = BlueMedium,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_player_two),
                contentDescription = null
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (showSecondOpinion) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = secondOrangeOpinion.toString(),
                            color = OrangeMedium,
                            fontSize = 26.sp,
                            fontFamily = kineks
                        )

                        Text(
                            text = "%",
                            color = OrangeMedium,
                            fontSize = 20.sp,
                            fontFamily = kineks
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_question),
                        contentDescription = null,
                        tint = OrangeMedium,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}
