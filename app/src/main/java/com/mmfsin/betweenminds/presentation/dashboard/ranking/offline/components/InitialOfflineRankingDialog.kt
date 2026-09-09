package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline.components

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
import androidx.compose.ui.graphics.Color
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
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun InitialOfflineRankingDialogPV() {
    InitialOfflineRankingDialog(
        {}, {}, {}, true
    )
}

@Composable
fun InitialOfflineRankingDialog(
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
                text = R.string.selector_ranking,
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
                    text = "bla bla blu bli bla bla bla",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
                )

                SpacerSmall()

                Text(
                    text = "bla bla blu bli bla bla bla",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 18.sp
                    ),
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

@Composable
fun SetPlayerName(
    name: String,
    onNameChange: (String) -> Unit,
    hint: Int,
    color: Color,
    enabled: Boolean = true
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(R.drawable.ic_human_down), null,
            tint = color,
            modifier = Modifier.size(42.dp)
        )

        SpacerSmall(horizontal = true)

        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .border(width = 1.dp, color = color, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            value = name,
            onValueChange = { onNameChange(it.take(20)) },
            enabled = enabled,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = color,
                fontSize = 18.sp,
                fontFamily = alphazet
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            cursorBrush = SolidColor(color),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (name.isEmpty()) {
                        Text(
                            text = stringResource(hint),
                            modifier = Modifier.alpha(0.5f),
                            color = color,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}